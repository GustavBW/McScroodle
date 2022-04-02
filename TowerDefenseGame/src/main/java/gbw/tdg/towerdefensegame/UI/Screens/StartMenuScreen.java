package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.GScene;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.StartGameButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartMenuScreen extends GScene {

    private static final double renderingPriority = 75D;
    private final StartGameButton startGameButton;
    private final RText titleText;

    public StartMenuScreen(){

        Point2D sGBPos = new Point2D(Main.canvasSize.getX() * 0.5,Main.canvasSize.getY() * 0.65);
        RText sGBText = new RText("PLAY", sGBPos.subtract(-18,5),5, Color.WHITE, Font.font("Impact", 140.0));

        Point2D titlePos = new Point2D(Main.canvasSize.getX() * 0.32, Main.canvasSize.getY() * 0.15);
        titleText = new RText("\t\t Σ\nTOWER DEFENCE", titlePos, 5, Color.WHITE, Font.font("Impact", 180));

        this.startGameButton = new StartGameButton(sGBPos, Main.canvasSize.getX() * 0.2,Main.canvasSize.getY() * 0.1,sGBText);
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        titleText.render(gc);
        startGameButton.render(gc);
    }

    @Override
    public Point2D getPosition(){
        return new Point2D(0,0);
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    @Override
    public void spawn(){
        Renderable.newborn.add(this);
        startGameButton.spawn();
    }

    @Override
    public void destroy() {
        startGameButton.destroy();
        Renderable.expended.add(this);
    }

}
