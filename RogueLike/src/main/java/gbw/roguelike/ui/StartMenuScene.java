package gbw.roguelike.ui;

import gbw.roguelike.Main;
import gbw.roguelike.enums.SceneType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class StartMenuScene extends GScene {

    private StartGameButton sgButton;

    public StartMenuScene(){
        super.type = SceneType.MAIN_MENU;

        double sgWidth = 0.1;
        double sgHeight = 0.1;
        sgButton = new StartGameButton(
                new Point2D(Main.canvasDim.getX() * (0.5 - sgWidth * 0.5), Main.canvasDim.getY() * (0.5 - sgHeight * 0.5)),
                new Point2D(Main.canvasDim.getX() * sgWidth, Main.canvasDim.getY() * sgHeight),
                new TextElement("PLAY", Font.font("Impact", FontPosture.ITALIC, 36D), new Color(1,1,1, 1))
        );

    }

    @Override
    public void activateScene() {
        sgButton.instantiate();
    }

    @Override
    public void deactivateScene() {
        sgButton.destroy();
    }

    @Override
    public void render(GraphicsContext gc) {
        sgButton.render(gc);
    }
}
