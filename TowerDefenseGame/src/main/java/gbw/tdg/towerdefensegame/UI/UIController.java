package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class UIController implements Tickable{

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
        createUI();
    }

    public void render(GraphicsContext gc){
        mainHealthBar.render(gc);
        screenToShow.render(gc);
    }

    public Point2D getPosition() {
        return new Point2D(0,0);
    }

    private void createUI(){
        //mainHealthBar = new FancyProgressBar(Main.canvasSize.getX() - 10, 50, new Point2D(5,5), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1), new Color(255 / 255.0,69 / 255.0,0,1));
        startMenuScreen = new StartMenuScreen();
        gameOverScreen = new GameOverScreen();
        inGameScreen = new InGameScreen();
    }

    public void changeScene(GameState state){
        removeCurrentUI();
        switch (state){
            case START_MENU -> screenToShow = startMenuScreen;
            case IN_GAME -> screenToShow = inGameScreen;
            case GAME_OVER -> screenToShow = gameOverScreen;
        }
        screenToShow.spawn();
    }

    private void removeCurrentUI(){
        for(Renderable r : effects){
            r.destroy();
        }
        effects.clear();

        if(screenToShow != null) {
            screenToShow.destroy();
        }
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

    @Override
    public void tick() {

    }
}
