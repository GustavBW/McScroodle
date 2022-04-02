package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.GScene;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DevInfoScreen extends GScene implements Tickable {

    private final double renderingPriority = 99.9D;
    private final RText attrText1, attrValText;
    private long lastCall = 0;

    public DevInfoScreen(){
        attrText1 = new RText(
            "FPS: ", new Point2D(50,50), 2, Color.GREENYELLOW, Font.font("Verdana",22)
        );
        attrValText = new RText(
                "", new Point2D(150,50), 2, Color.GREENYELLOW, Font.font("Verdana",22)
        );
    }


    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
    }

    @Override
    public void tick(){
        if(lastCall + 500_000_000 <= System.nanoTime()) {
            attrValText.setText("" + Main.FPS);
            lastCall = System.nanoTime();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        attrText1.render(gc);
        attrValText.render(gc);
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(0,0);
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
}
