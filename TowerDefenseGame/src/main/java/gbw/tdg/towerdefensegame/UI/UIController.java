package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class UIController implements Tickable {

    private final Main game;
    private FancyProgressBar mainHealthBar;
    private final ArrayList<Renderable> effects;
    private Renderable screenToShow;
    private StartMenuScreen startMenuScreen;
    private GameOverScreen gameOverScreen;
    private InGameScreen inGameScreen;


    public UIController(Main game){
        this.game = game;

        effects = new ArrayList<>();
        Main.addTickable.add(this);
        createUI();
    }

    public void render(GraphicsContext gc){
        mainHealthBar.render(gc);

        screenToShow.render(gc);
    }

    public void tick(){
        mainHealthBar.setVal((double) Main.HP / Main.MAXHP);
    }

    private void createUI(){
        mainHealthBar = new FancyProgressBar(Main.canvasSize.getX() - 10, 50, new Point2D(5,5), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1), new Color(255 / 255.0,69 / 255.0,0,1));
        startMenuScreen = new StartMenuScreen();
        gameOverScreen = new GameOverScreen();
        inGameScreen = new InGameScreen();
    }

    public void showGameOver(){
        removeCurrentUI();
        screenToShow = gameOverScreen;
    }

    public void showStartMenu(){
        removeCurrentUI();
        screenToShow = startMenuScreen;
    }

    public void showInGame(){
        removeCurrentUI();
        screenToShow = inGameScreen;
    }

    private void removeCurrentUI(){
        for(Renderable r : effects){
            r.destroy();
        }
        effects.clear();
    }
}
