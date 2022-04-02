package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.Screens.DevInfoScreen;
import gbw.tdg.towerdefensegame.UI.Screens.GameOverScreen;
import gbw.tdg.towerdefensegame.UI.Screens.InGameScreen;
import gbw.tdg.towerdefensegame.UI.Screens.StartMenuScreen;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class UIController implements Tickable{

    private final Main game;
    private FancyProgressBar mainHealthBar;
    private final ArrayList<Renderable> effects;
    private Renderable screenToShow;
    private StartMenuScreen startMenuScreen;
    private GameOverScreen gameOverScreen;
    private InGameScreen inGameScreen;
    private DevInfoScreen devInfoScreen;
    private boolean showInfo = false;


    public UIController(Main game){
        this.game = game;

        effects = new ArrayList<>();
        createUI();
    }

    public void render(GraphicsContext gc){
        mainHealthBar.render(gc);
        screenToShow.render(gc);
    }

    public void toggleInfo(){
        showInfo = !showInfo;

        if(showInfo){
            devInfoScreen.spawn();
        }else{
            devInfoScreen.destroy();
        }
    }

    public Point2D getPosition() {
        return new Point2D(0,0);
    }

    private void createUI(){
        //mainHealthBar = new FancyProgressBar(Main.canvasSize.getX() - 10, 50, new Point2D(5,5), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1), new Color(255 / 255.0,69 / 255.0,0,1));
        startMenuScreen = new StartMenuScreen();
        gameOverScreen = new GameOverScreen();
        inGameScreen = new InGameScreen();
        devInfoScreen = new DevInfoScreen();
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
