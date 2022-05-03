package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.scenes.DevInfoScreen;
import gbw.tdg.towerdefensegame.UI.scenes.GameOverScreen;
import gbw.tdg.towerdefensegame.UI.scenes.InGameScreen;
import gbw.tdg.towerdefensegame.UI.scenes.StartMenuScreen;

public class UIController implements Tickable{

    private final Main game;
    private Renderable screenToShow;
    private StartMenuScreen startMenuScreen;
    private GameOverScreen gameOverScreen;
    private InGameScreen inGameScreen;
    private DevInfoScreen devInfoScreen;
    private boolean showInfo = false;
    private WaveManager waveManager;


    public UIController(Main game, WaveManager waveManager){
        this.game = game;
        this.waveManager = waveManager;
        createUI();
    }

    public void toggleInfo(){
        showInfo = !showInfo;

        if(showInfo){
            devInfoScreen.spawn();
        }else{
            devInfoScreen.destroy();
        }
    }

    private void createUI(){
        startMenuScreen = new StartMenuScreen();
        gameOverScreen = new GameOverScreen();
        inGameScreen = new InGameScreen(waveManager);
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
