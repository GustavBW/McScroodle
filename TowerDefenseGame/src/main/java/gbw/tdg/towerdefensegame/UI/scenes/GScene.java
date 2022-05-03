package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.RCollection;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GScene implements Renderable {

    protected Point2D position;
    protected double sizeX, sizeY, renderingPriority;
    protected final RCollection group = new RCollection();
    protected Color background;

    public GScene(double rP){
        this.position = new Point2D(0,0);
        this.renderingPriority = rP;
        background = Color.GREY;
    }

    public GScene(Point2D position, double renderingPriority){
        this(renderingPriority);
        this.position = position;
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        group.spawn();
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        group.destroy();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(background);
        gc.fillRect(0,0, Main.canvasSize.getX(),Main.canvasSize.getY());
    }

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
