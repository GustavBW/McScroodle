package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.scenes.*;

import java.util.ArrayList;
import java.util.List;

public class UIController implements Tickable{

    private final static List<GameState> stateTracker = new ArrayList<>();
    private final Main game;
    private GScene screenToShow;
    private StartMenuScreen startMenuScreen;
    private GameOverScreen gameOverScreen;
    private InGameScreen inGameScreen;
    private DevInfoScreen devInfoScreen;
    private InvocationManagementScene invoManWindow;
    private AugmentManagementScene augManWindow;
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
        invoManWindow = new InvocationManagementScene(0);
        augManWindow = new AugmentManagementScene();
    }

    public void changeScene(GameState state){
        removeCurrentUI();
        stateTracker.add(state);
        switch (state){
            case START_MENU -> screenToShow = startMenuScreen;
            case IN_GAME -> screenToShow = inGameScreen;
            case GAME_OVER -> screenToShow = gameOverScreen;
            case INVOCATION_MANAGEMENT -> screenToShow = invoManWindow;
            case AUGMENT_MANAGEMENT -> screenToShow = augManWindow;
        }
        screenToShow.spawn();
    }

    private void removeCurrentUI(){
        if(screenToShow != null) {
            screenToShow.destroy();
        }
    }

    public static GameState getPrevious(){
        if(stateTracker.size() - 2 < 0){
            return GameState.VOID;
        }
        return stateTracker.get(stateTracker.size() - 2);
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
