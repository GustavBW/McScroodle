package gbw.tdg.towerdefensegame.UI.vfx;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ConnectingLine extends VFX{

    private Point2D start, end;
    private Renderable obj1,obj2;

    public ConnectingLine(int lifetime, double rendPrio, Point2D start, Point2D end) {
        super(lifetime, rendPrio);
        this.start = start;
        this.end = end;
    }

    public ConnectingLine(int lifetime, double rendPrio, Renderable obj1, Renderable obj2){
        super(lifetime, rendPrio);
        this.start = obj1 == null ? Point2D.ZERO : obj1.getPosition();
        this.end = obj2 == null ? Point2D.ZERO : obj2.getPosition();
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public ConnectingLine(int lifetime, double rendPrio, Point2D start, Renderable obj2){
        super(lifetime,rendPrio);
        this.start = start;
        this.obj2 = obj2;
        this.end = obj2 == null ? Point2D.ZERO : obj2.getPosition();
    }

    public ConnectingLine(int lifetime, double rendPrio, Renderable obj1, Point2D end){
        super(lifetime,rendPrio);
        this.obj1 = obj1;
        this.start = obj1 == null ? Point2D.ZERO : obj1.getPosition();
        this.end = end;
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.AQUA);
        gc.strokeLine(start.getX(), start.getY(),end.getX(),end.getY());
    }

    @Override
    public void tick(){
        super.tick();

        if(obj1 != null) {
            start = accountForDim(obj1);
        }
        if(obj2 != null){
            end = accountForDim(obj2);
        }
    }

    private Point2D accountForDim(Renderable r){
        return r.getPosition().add(r.getDimensions().multiply(0.5));
    }

    @Override
    public Point2D getDimensions(){
        return new Point2D(start.distance(end),0);
    }
}
