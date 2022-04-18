package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.StartGameButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartMenuScreen extends GScene {

    private final StartGameButton startGameButton;
    private final RText titleText;

    public StartMenuScreen(){
        super(75);
        Point2D sGBPos = new Point2D(Main.canvasSize.getX() * 0.5,Main.canvasSize.getY() * 0.65);
        RText sGBText = new RText("PLAY", Point2D.ZERO,5, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.05));

        Point2D titlePos = new Point2D(Main.canvasSize.getX() * 0.42, Main.canvasSize.getY() * 0.15);
        titleText = new RText("\t\t Σ\nTOWER DEFENCE", titlePos, 5, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.0537));

        this.startGameButton = new StartGameButton(sGBPos, Main.canvasSize.getX() * 0.2,Main.canvasSize.getY() * 0.1,sGBText);
        startGameButton.setPosition(sGBPos.subtract(startGameButton.getDimensions().multiply(0.5)));
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        titleText.render(gc);
        startGameButton.render(gc);
    }

    @Override
    public void spawn(){
        super.spawn();
        startGameButton.spawn();
    }

    @Override
    public void destroy() {
        startGameButton.destroy();
        super.destroy();
    }

}
