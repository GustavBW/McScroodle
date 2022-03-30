package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IGameObject;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class InGameScreen extends GScene implements Tickable {

    private static final double renderingPriority = 75D;
    private final FancyProgressBar mainHealthBar;

    public InGameScreen(){
        mainHealthBar = new FancyProgressBar(Main.canvasSize.getX() - 10, 50, new Point2D(5,5), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1), new Color(255 / 255.0,69 / 255.0,0,1));
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public Point2D getPosition() {
        return new Point2D(0,0);
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
        mainHealthBar.spawn();
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
        mainHealthBar.destroy();
    }

    @Override
    public void tick() {

    }
}
