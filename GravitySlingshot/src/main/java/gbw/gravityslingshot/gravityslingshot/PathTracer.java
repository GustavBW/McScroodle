package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PathTracer {

    private final Canvas canvas;
    private WritableImage lastTrace;

    public PathTracer(Canvas canvas){
        this.canvas = canvas;
    }

    public void traceLevel(List<GravityObject> tracables){
        List<List<Point2D>> pointsTraced = new ArrayList<>(tracables.size());

        for(GravityObject g : tracables){
            pointsTraced.add(g.getOrbit().getPath());
        }

        lastTrace = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
        PixelWriter pW = lastTrace.getPixelWriter();

        for(List<Point2D> listOfPoints : pointsTraced){
            for(Point2D point : listOfPoints){
                if(!(point.getX() > lastTrace.getWidth() || point.getX() < 0
                    || point.getY() > lastTrace.getHeight() || point.getY() < 0))
                {
                    pW.setColor((int) point.getX(), (int) point.getY(), Color.BLACK);
                }
            }
        }

        canvas.getGraphicsContext2D().drawImage(lastTrace,0,0);
    }

}
