package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class TowerDisplay implements Renderable {

    private TowerStatDisplay statDisplay;
    private TowerRangeIndicator rangeIndicator;
    private TowerFunctionsDisplay functionsDisplay;
    private final Tower tower;

    public TowerDisplay(Tower tower){
        this.statDisplay = new TowerStatDisplay(tower, new Point2D(Main.canvasSize.getX()*.01, Main.canvasSize.getY()*.3));
        this.rangeIndicator = new TowerRangeIndicator(tower,tower.getPosition().add(tower.getDimensions().multiply(0.5)));
        this.functionsDisplay = new TowerFunctionsDisplay(tower,new Point2D(Main.canvasSize.getX()*.01,statDisplay.getExtremeties().getY()));
        this.tower = tower;
    }

    @Override
    public void spawn() {
        statDisplay.spawn();
        rangeIndicator.spawn();
        functionsDisplay.spawn();
        rangeIndicator.setDimensions(new Point2D(tower.getRange(),0));
    }

    @Override
    public void destroy() {
        statDisplay.destroy();
        rangeIndicator.destroy();
        functionsDisplay.destroy();
    }

    @Override
    public void render(GraphicsContext gc) {}

    @Override
    public Point2D getPosition() {
        return tower.getPosition();
    }
    public TowerStatDisplay getStatDisplay() {
        return statDisplay;
    }
    public TowerRangeIndicator getRangeIndicator() {
        return rangeIndicator;
    }
    public TowerFunctionsDisplay getFunctionsDisplay() {
        return functionsDisplay;
    }

    @Override
    public double getRenderingPriority() {
        return 0;
    }

    @Override
    public void setPosition(Point2D newPos) {
        rangeIndicator.setPosition(newPos.add(tower.getDimensions().multiply(0.5)));
    }

    @Override
    public void setRenderingPriority(double newPrio) {
        rangeIndicator.setRenderingPriority(newPrio);
        statDisplay.setRenderingPriority(newPrio +.1);
        functionsDisplay.setRenderingPriority(newPrio +.1);
    }

    @Override
    public void setDimensions(Point2D dim) {

    }

    @Override
    public Point2D getDimensions() {
        return Point2D.ZERO;
    }
}
