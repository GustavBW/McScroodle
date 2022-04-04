package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.MenuButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScreen extends GScene {

    private MenuButton menuButton;
    private RText youDiedText;
    private Color backgroundColor;

    public GameOverScreen(){
        super(75);
        backgroundColor = new Color(0,0,0,0.5);
        Point2D buttonPos = new Point2D(Main.canvasSize.getX() * 0.5,Main.canvasSize.getY() * 0.83);

        RText textUnit = new RText("MENU", buttonPos,3,new Color(1,1,1,1), Font.font("Impact", 86.00));
        this.menuButton = new MenuButton(buttonPos, Main.canvasSize.getX() * 0.16,Main.canvasSize.getY() * 0.08, textUnit);

        youDiedText = new RText("YOU DIED", new Point2D(Main.canvasSize.getX() * 0.455,Main.canvasSize.getY() * 0.13), 5,new Color(1,0,0,1), Font.font("Impact", 140.0));
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        menuButton.render(gc);
        youDiedText.render(gc);
    }

    @Override
    public void spawn() {
        super.spawn();
        menuButton.spawn();
    }

    @Override
    public void destroy() {
        super.destroy();
        menuButton.destroy();
    }

}
