package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DevInfoScreen extends GScene implements Tickable {

    private final RText attrText1, attrValText;
    private long lastCall = 0;

    public DevInfoScreen(){
        super(99.9);
        attrText1 = new RText(
            "FPS: ", new Point2D(50,100), 2, Color.GREENYELLOW, Font.font("Verdana",22)
        );
        attrValText = new RText(
                "", new Point2D(150,100), 2, Color.GREENYELLOW, Font.font("Verdana",22)
        );
    }


    @Override
    public void spawn() {
        super.spawn();
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        super.destroy();
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
    public double getRenderingPriority() {
        return renderingPriority;
    }
}
