package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Clickable{

    private WayPoint latest;
    private WayPoint next;
    private double x,y;
    private final double mvspeed = 10, minDistToPoint = 5, size = 20;
    private final Path path;
    private final ProgressBar hpBar;
    private int hp;

    public Enemy(double x, double y, Path path){
        this.x = x;
        this.y = y;
        this.path = path;
        latest = path.getStart();
        next = path.getNext(latest);
        hpBar = new ProgressBar(0,10,30, true, new Point2D(x,y - 10));

    }

    public void tick(){

        if(hp <= 0){destroy();}

        Point2D dir = checkDistanceToNext();

        if(dir != null) {
            dir = dir.normalize();

            dir = dir.multiply(mvspeed);

            x += dir.getX();
            y += dir.getY();
        }

        hpBar.setVal(hp);
        hpBar.setPosition(new Point2D(x,y - 10));
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

    private void destroy(){
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
}
