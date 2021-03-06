package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScreen implements Renderable, Clickable {

    private MenuButton menuButton;
    private RText youDiedText;
    private Color backgroundColor;

    public GameOverScreen(){
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
    public boolean isInBounds(Point2D pos) {
        return false;
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void destroy() {
        menuButton.destroy();
    }

    @Override
    public void reInstantiate() {
        menuButton.reInstantiate();
    }
}
