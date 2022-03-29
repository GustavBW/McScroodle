package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
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
    private final double pathLength;

    public Path(int numOfWayPoints){
        this.points = sortById(createWayPoints(numOfWayPoints));
        this.start = points.get(0);
        this.end = points.get(points.size() - 1);
        this.pathLength = calcPathLength();
        System.out.println("Path generated with length = " + pathLength);
    }

    public void render(GraphicsContext gc){

        gc.setFill(Color.GRAY);

        for(int i = 0; i < points.size() - 2; i++){
            gc.strokeLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y);
        }

        gc.strokeLine(points.get(points.size() -2 ).x, points.get(points.size() -2 ).y, end.x, end.y);

    }

    public WayPoint getStart(){return start;}
    public WayPoint getEnd(){return end;}
    public WayPoint getNext(WayPoint current){
        return current.getId() + 1 >= end.getId() ? end : points.get(current.getId());
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
    private ArrayList<WayPoint> createWayPoints(int numOfWayPoints){

        ArrayList<WayPoint> wayPoints = new ArrayList<>();
        wayPoints.add(new WayPoint(0,0,1));

        ArrayList<WayPoint> list = new ArrayList<>();

        for(int i = 2; i < numOfWayPoints + 1; i++){
            list.add(new WayPoint(Main.random.nextInt((int) Main.canvasSize.getX()), Main.random.nextInt((int) Main.canvasSize.getY()), i));
        }

        wayPoints.addAll(list);
        wayPoints.add(new WayPoint(Main.canvasSize.getX()* 0.9,  Main.canvasSize.getY() * 0.9, numOfWayPoints));

        return wayPoints;
    }

    public double getPathLength(){return pathLength;}

    private double calcPathLength(){

        double output = 0;

        for(int i = 0; i < points.size() - 2; i++){

            WayPoint p1 = points.get(i);
            WayPoint p2 = points.get(i + 1);

            Point2D dist = new Point2D((p1.x - p2.x),(p1.y - p2.y));

            output += dist.magnitude();

        }

        return output;
    }

}
