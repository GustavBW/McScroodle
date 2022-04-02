package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Clickable,Tickable,IEnemy{

    private final static double renderingPriority = 40D;
    private WayPoint latest;
    private WayPoint next;
    private double x,y;
    private double mvspeed = 15, minDistToPoint = 10, size = 40;
    private final Path path;
    private final ProgressBar hpBar;
    private int maxHP = 10, id, hp = maxHP;
    private double lengthTraveled = 0;
    private static int enemyCount = 0;
    private boolean alive = true,selected = false;
    private final Color color;

    public Enemy(double x, double y, Path path){
        this.x = x;
        this.y = y;
        this.path = path;
        this.color = new Color(Main.random.nextInt(1,255)/255.0,0,Main.random.nextInt(1,255) / 255.0,1);
        latest = path.getStartPoint();
        next = path.getNext(latest);
        hpBar = new FancyProgressBar(size * 2, size * 0.2,new Point2D(x,y), color,
                new Color(0 / 255.0,0/255.0,0,0.8));

        enemyCount++;
        this.id = enemyCount;
    }
    @Override
    public void tick(){
        if(hp <= 0){
            alive = false;
            Main.alterGoldAmount(maxHP);
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
            hpBar.setPosition(new Point2D(x - (size * 0.5),y - (size * 0.5)));
        }
    }
    @Override
    public void render(GraphicsContext gc){
        if(selected){
            gc.setFill(Color.GOLD);
            gc.fillRect(x-4,y-4,size+4,size+4);
        }

        gc.setFill(color);
        gc.fillRect(x,y,size,size);

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

        if(latest == path.getEndPoint()){
            completedRun();
            return null;
        }

        return new Point2D(next.x - x, next.y - y);
    }

    public void applyBuff(EnemyBuff buff){
        hp += buff.getHealth();
        maxHP += buff.getHealth();
        mvspeed += buff.getBonusSpeed();
    }

    private void completedRun(){
        Main.HP--;
        destroy();
    }

    @Override
    public void spawn() {
        Clickable.newborn.add(this);
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
        IEnemy.newborn.add(this);
    }

    public void destroy(){
        Clickable.expended.add(this);
        Tickable.expended.add(this);
        Renderable.expended.add(this);
        IEnemy.expended.add(this);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() < x + size && pos.getX() > x) && (pos.getY() < y + size && pos.getY() > y);
    }

    @Override
    public void onInteraction() {
        selected = !selected;
        if(!Main.onPause) {
            hp--;
        }
        System.out.println("OOF");
    }

    public Point2D getPosition(){return new Point2D(x,y);}

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public double getProgress(){
        return lengthTraveled / path.getPathLength();
    }
    @Override
    public int getHp(){return hp;}
    @Override
    public void changeHp(double amount){hp += amount;}
    @Override
    public double getMvspeed(){
        return mvspeed;
    }
    @Override
    public double getSize(){return size;}
    @Override
    public void deselect(){
        selected = false;
    }

    @Override
    public String toString(){return "Enemy " + id;}
}
