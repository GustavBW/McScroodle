package gbw.tdg.towerdefensegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Path {

    private ArrayList<WayPoint> points;
    private final WayPoint start;
    private final WayPoint end;

    public Path (ArrayList<WayPoint> points){
        this.points = sortById(points);
        this.start = points.get(0);
        this.end = points.get(points.size() -1);

        for(WayPoint p : points){
            System.out.println(p);
        }
    }

    public void render(GraphicsContext gc){

        gc.setFill(Color.GRAY);

        for(int i = 0; i < points.size() - 2; i++){
            gc.strokeLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y);
        }

        gc.strokeLine(points.get(points.size() -2 ).x, points.get(points.size() -2 ).y,
                end.x, end.y);

    }

    public WayPoint getStart(){return start;}
    public WayPoint getEnd(){return end;}

    public WayPoint getNext(WayPoint current){

        return current.getId() + 1 == end.getId() ? end : points.get(current.getId());
    }

    private ArrayList<WayPoint> sortById(ArrayList<WayPoint> list){
        WayPoint[] tempArray = new WayPoint[list.size()];
        ArrayList<WayPoint> output = new ArrayList<>();

        for(WayPoint p : list){
            tempArray[p.getId() - 1] = p;
        }

        Collections.addAll(output, tempArray);

        return output;
    }

}
