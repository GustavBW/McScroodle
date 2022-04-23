package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TowerRangeIndicator implements Renderable {

    private double renderingPriority = 56;
    private Point2D position;
    private double range;
    private final Tower tower;
    private Color color = new Color(0,0,0,0.28);

    public TowerRangeIndicator(Tower tower, Point2D position){
        this.position = position;
        this.range = tower.getRange();
        this.tower = tower;
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
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRoundRect(position.getX() - range, position.getY() - range, range * 2, range * 2, range * 2, range* 2);
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
        this.range = dim.getX();
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(range,0);
    }
}
