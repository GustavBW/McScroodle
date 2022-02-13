package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScreen implements Renderable{

    private MenuButton menuButton;
    private RText youDiedText;
    private Color backgroundColor;

    public GameOverScreen(){
        backgroundColor = new Color(0,0,0,0.01);
        Point2D buttonPos = new Point2D(Main.canvasSize.getX() * 0.5,Main.canvasSize.getY() * 0.63);

        RText textUnit = new RText("MENU", buttonPos,3,new Color(1,1,1,1), Font.font("Impact", 86.00));
        this.menuButton = new MenuButton(buttonPos, Main.canvasSize.getX() * 0.16,Main.canvasSize.getY() * 0.07, textUnit);

        youDiedText = new RText("YOU DIED", new Point2D(Main.canvasSize.getX() * 0.455,Main.canvasSize.getY() * 0.3), 5,new Color(1,0,0,1), Font.font("Impact", 140.0));
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        menuButton.render(gc);
        youDiedText.render(gc);
    }
}
