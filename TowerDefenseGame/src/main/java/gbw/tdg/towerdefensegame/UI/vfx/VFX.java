package gbw.tdg.towerdefensegame.UI.vfx;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class VFX implements Renderable, Tickable {

    public static Color defaultColor = new Color(1,1,0.5,0.5);
    private long killTimeStamp;
    private int lifetime = 1;
    private double rendPrio = 50;
    protected Point2D position = Point2D.ZERO, dim = Point2D.ZERO;

    public VFX(int lifetime, double rendPrio){
        this.lifetime = lifetime;
        this.rendPrio = rendPrio;
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() >= killTimeStamp){
            destroy();
        }
    }

    @Override
    public void spawn() {
        killTimeStamp = System.currentTimeMillis() + lifetime;
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
    }

    @Override
    public void render(GraphicsContext gc) {}

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return rendPrio;
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }

    @Override
    public void setRenderingPriority(double newPrio) {
        this.rendPrio = newPrio;
    }

    @Override
    public void setDimensions(Point2D dim) {
        this.dim = dim;
    }

}
