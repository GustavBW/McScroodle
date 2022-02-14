package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Clickable, Tickable, Destroyable{

    private WayPoint latest;
    private WayPoint next;
    private double x,y;
    private final double mvspeed = 15, minDistToPoint = 10, size = 40;
    private final Path path;
    private final ProgressBar hpBar;
    private int hp = 10, id, maxHP = 10;
    private double lengthTraveled = 0;
    private static int enemyCount = 0;
    private boolean alive = true;

    public Enemy(double x, double y, Path path){
        this.x = x;
        this.y = y;
        this.path = path;
        latest = path.getStart();
        next = path.getNext(latest);
        hpBar = new FancyProgressBar(100, 15,new Point2D(x,y - 10),new Color(211 / 255.0,0,0,1), new Color(211 / 255.0,88/255.0,0,0.8));
        Main.addClickable.add(this);
        Main.addTickable.add(this);

        enemyCount++;
        this.id = enemyCount;
    }

    public void tick(){

        if(hp <= 0 || next == null){
            alive = false;
            destroy();
        }

        if(alive) {
            Point2D dir = checkDistanceToNext();

            if (dir != null) {
                dir = dir.normalize();

                dir = dir.multiply(mvspeed);

                x += dir.getX();
                y += dir.getY();
                lengthTraveled += new Point2D(dir.getX(), dir.getY()).magnitude();
            }

            hpBar.setVal((double) hp / maxHP);
            hpBar.setPosition(new Point2D(x, y - 10));
        }
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillRect(x-10,y-10,size,size);

        hpBar.render(gc);
    }


    private Point2D checkDistanceToNext(){
        double distX = (next.x - x) * (next.x - x);
        double distY = (next.y - y) * (next.y - y);
        double distance = Math.sqrt(distX + distY);

        if(distance < minDistToPoint){
            latest = next;
            next = path.getNext(next);
        }

        if(next == null){
            completedRun();
            return null;
        }

        return new Point2D(next.x - x, next.y - y);
    }

    private void completedRun(){
        Main.HP--;
        destroy();
    }

    public void destroy(){
        Main.removeEnemy.add(this);
        Main.removeClickable.remove(this);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() < x + size && pos.getX() > x) && (pos.getY() < y + size && pos.getY() > y);
    }

    @Override
    public void onInteraction() {
        hp--;
    }

    public Point2D getPosition(){return new Point2D(x,y);}

    public double getProgress(){
        return lengthTraveled / path.getPathLength();
    }

    public int getHp(){return hp;}
    public void changeHp(double amount){hp += amount;}
    public double getMvspeed(){
        return mvspeed;
    }
    public double getSize(){return size;}


    @Override
    public String toString(){return "Enemy " + id;}
}
