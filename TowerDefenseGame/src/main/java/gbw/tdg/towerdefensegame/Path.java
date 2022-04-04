package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<WayPoint> points;
    private WayPoint startPoint;
    private WayPoint endPoint;
    private final double pathLength;

    public Path(int numOfWayPoints){
        this.points = createWayPoints(numOfWayPoints);
        this.startPoint = points.get(0);
        this.endPoint = points.get(points.size() - 1);
        this.pathLength = calcPathLength();
        System.out.println("Path generated with length = " + pathLength);
    }

    public void render(GraphicsContext gc){

        gc.setFill(Color.GRAY);

        for(WayPoint wP : points){
            wP.render(gc);
        }

        for(int i = 0; i < points.size() - 2; i++){
            gc.strokeLine(points.get(i).x,points.get(i).y,points.get(i+1).x,points.get(i+1).y);
        }

        gc.strokeLine(points.get(points.size() -2 ).x, points.get(points.size() -2 ).y, endPoint.x, endPoint.y);

    }

    public WayPoint getStartPoint(){return startPoint;}
    public WayPoint getEndPoint(){return endPoint;}
    public WayPoint getNext(WayPoint current){
        return current.getId() + 1 >= endPoint.getId() ? endPoint : points.get(current.getId());
    }

    private ArrayList<WayPoint> createWayPoints(int numOfWayPoints){

        ArrayList<WayPoint> wayPoints = new ArrayList<>();
        startPoint = new WayPoint(0,0,1);
        //System.out.println("Made " + startPoint);
        wayPoints.add(startPoint);

        ArrayList<WayPoint> list = new ArrayList<>();
        for(int i = 2; i < numOfWayPoints; i++){
            WayPoint midwayWP = new WayPoint(Main.random.nextInt((int) Main.canvasSize.getX()), Main.random.nextInt((int) Main.canvasSize.getY()), i);
            //System.out.println("Made " + midwayWP);
            list.add(midwayWP);
        }

        wayPoints.addAll(list);
        endPoint = new WayPoint(Main.canvasSize.getX()* 0.9,  Main.canvasSize.getY() * 0.9, numOfWayPoints);
        //System.out.println("Made " + endPoint);
        wayPoints.add(endPoint);

        return wayPoints;
    }

    public double getPathLength(){return pathLength;}
    public List<WayPoint> getPoints(){return points;}
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
