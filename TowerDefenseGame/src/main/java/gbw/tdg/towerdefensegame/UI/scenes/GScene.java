package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class GScene implements Renderable {

    protected Point2D position;
    protected double sizeX, sizeY, renderingPriority;

    public GScene(double rP){
        this.position = new Point2D(0,0);
        this.renderingPriority = rP;
    }

    public GScene(Point2D position, double renderingPriority){
        this.position = position;
        this.renderingPriority = renderingPriority;
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
    }

    @Override
    public void render(GraphicsContext gc) {}

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }

    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(sizeX,sizeY);
    }
}
