package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.tower.Tower;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.DeleteTowerButton;
import gbw.tdg.towerdefensegame.UI.buttons.MoveTowerButton;
import gbw.tdg.towerdefensegame.UI.buttons.SetTowerTargetingButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class TowerFunctionsDisplay implements Renderable {

    private double renderingPriority = 57;
    private final Tower tower;
    private Point2D position,
            moveButtonOffset = new Point2D(0,0),
            deleteButtonOffset = new Point2D(0,0);
    private final GraphicalInventory<Button> menu;
    private double sizeX,sizeY;

    public TowerFunctionsDisplay(Tower t,Point2D pos){
        this.sizeX = Main.canvasSize.getX() * 0.1;
        this.sizeY = Main.canvasSize.getY() * 0.1;
        this.menu = new GraphicalInventory<>(1,sizeX,sizeY,5,pos,renderingPriority,3);
        this.tower = t;
        this.position = pos;
        menu.addAll(List.of(
                new MoveTowerButton(this),
                new DeleteTowerButton(this),
                new SetTowerTargetingButton(this)
        ));
        menu.setBackgroundColor(Color.TRANSPARENT);
    }


    public void onTowerMove() {

    }

    public void onTowerDelete(){
        tower.sell();
    }

    public Tower getTower(){
        return tower;
    }

    @Override
    public void spawn() {
        menu.spawn();
    }

    @Override
    public void destroy() {
        menu.destroy();
    }

    @Override
    public void render(GraphicsContext gc) {

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
        position = p;
        menu.setPosition(p);
    }

    @Override
    public void setDimensions(Point2D dim) {
        menu.setDimensions(dim);
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(sizeX, sizeY);
    }
}
