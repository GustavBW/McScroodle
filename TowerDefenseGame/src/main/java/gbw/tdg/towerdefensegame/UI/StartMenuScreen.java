package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartMenuScreen implements Renderable, Clickable {

    private final StartGameButton startGameButton;
    private final RText titleText;

    public StartMenuScreen(){

        Point2D sGBPos = new Point2D(Main.canvasSize.getX() * 0.5,Main.canvasSize.getY() * 0.65);
        RText sGBText = new RText("PLAY", sGBPos.subtract(-18,5),5, Color.WHITE, Font.font("Impact", 140.0));

        Point2D titlePos = new Point2D(Main.canvasSize.getX() * 0.105, Main.canvasSize.getY() * 0.15);
        titleText = new RText("SOME TOWER DEFENCE GAME", titlePos, 5, Color.WHITE, Font.font("Impact", 180));

        this.startGameButton = new StartGameButton(sGBPos, Main.canvasSize.getX() * 0.2,Main.canvasSize.getY() * 0.1,sGBText);
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        titleText.render(gc);
        startGameButton.render(gc);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        return false;
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void destroy() {
        startGameButton.destroy();
    }

    @Override
    public void reInstantiate() {
        startGameButton.reInstantiate();
    }

}
