package gbw.roguelike.ui;

import gbw.roguelike.Main;
import gbw.roguelike.enums.SceneType;
import gbw.roguelike.interfaces.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class InGameScene extends GScene implements Tickable {

    private final TextElement uiFpsText, inGameFpsText;

    public InGameScene(){
        this.uiFpsText = new TextElement("UI", new Point2D(Main.canvasDim.getX() * 0.3,50));
        this.inGameFpsText = new TextElement("GAME", new Point2D(Main.canvasDim.getX() * 0.6,50));
        type = SceneType.IN_GAME;
    }

    @Override
    public void render(GraphicsContext gc) {

        uiFpsText.render(gc);
        inGameFpsText.render(gc);

    }

    @Override
    public void tick() {
        uiFpsText.setText("UI | " + Main.uiFPS);
        inGameFpsText.setText("GAME | " + Main.inGameFPS);
    }

    @Override
    public void activateScene() {
        Tickable.tickables.add(this);
    }

    @Override
    public void deactivateScene() {
        Tickable.tickables.remove(this);
    }
}
