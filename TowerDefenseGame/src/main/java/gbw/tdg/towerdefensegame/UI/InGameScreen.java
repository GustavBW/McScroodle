package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class InGameScreen extends GScene{



    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public Point2D getPosition() {
        return new Point2D(0,0);
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
    }
}
