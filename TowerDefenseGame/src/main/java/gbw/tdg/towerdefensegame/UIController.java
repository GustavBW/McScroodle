package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class UIController implements Tickable{

    private final Main game;
    private FancyProgressBar mainHealthBar;
    private final ArrayList<Renderable> effects;

    public UIController(Main game){
        this.game = game;

        effects = new ArrayList<>();
        Main.addTickable.add(this);
        createUI();
    }

    public void render(GraphicsContext gc){
        mainHealthBar.render(gc);

        for(Renderable r : effects){
            r.render(gc);
        }
    }

    public void tick(){
        mainHealthBar.setVal((double) Main.HP / Main.MAXHP);
    }

    private void createUI(){
        mainHealthBar = new FancyProgressBar(game.canvasSize.getX() - 10, 50, new Point2D(5,5), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1), new Color(255 / 255.0,69 / 255.0,0,1));
    }

    public void showGameOver(){
        effects.add(new GameOverScreen());
    }
}
