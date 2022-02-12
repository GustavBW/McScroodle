package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy {

    private WayPoint latest;
    private WayPoint next;
    private double x,y;
    private final double mvspeed = 1, minDistToPoint = 10;
    private final Path path;

    public Enemy(double x, double y, Path path){
        this.x = x;
        this.y = y;
        this.path = path;
        latest = path.getStart();
        next = path.getNext(latest);
    }

    public void tick(){
        Point2D dir = checkDistanceToNext();
        dir = dir.normalize();

        x += dir.multiply(mvspeed).getX();
        y += dir.multiply(mvspeed).getY();
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillRect(x-10,y-10,20,20);
    }


    private Point2D checkDistanceToNext(){
        double distX = x * x - next.x * next.x;
        double distY = y * y - next.y * next.y;

        if(distX + distY < minDistToPoint * minDistToPoint){
            next = path.getNext(next);
        }

        return new Point2D(distX, distY);
    }
}
