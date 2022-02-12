package gbw.tdg.towerdefensegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Path {

    private ArrayList<WayPoint> points;
    private final WayPoint start;
    private final WayPoint end;

    public Path (ArrayList<WayPoint> points, WayPoint start, WayPoint end){
        this.points = points;
        this.start = start;
        this.end = end;
    }

    public void render(GraphicsContext gc){

        gc.setFill(Color.GRAY);


        gc.strokeLine(start.x, start.y, points.get(0).x,points.get(0).y);

        for(int i = 0; i < points.size() - 1; i++){
            gc.strokeLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y);
        }

        gc.strokeLine(points.get(points.size() -1 ).x, points.get(points.size() -1 ).y,end.x, end.y);

    }

    public WayPoint getStart(){return start;}
    public WayPoint getEnd(){return end;}

    public WayPoint getNext(WayPoint current){

        WayPoint nextWayPoint = points.get(0);

        for(int i = 0; i < points.size(); i++){
            if(current.x == points.get(i).x && current.y == points.get(i).y){
                nextWayPoint = i < points.size() - 1 ? points.get(i + 1) : end;
                break;
            }
        }

        return nextWayPoint;
    }

}
