package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.handlers.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    private static Main instance;
    public static RenderHandler renderHandler = new RenderHandler();
    public static Point2D canvasSize = new Point2D(1500,1000);
    public static Random random = new Random(System.currentTimeMillis());
    private Canvas canvas;
    private Stage mainStage;
    private Rectangle2D screenDim;
    private GraphicsContext gc;
    private MouseHandler mouseHandler;
    private KeyPressHandler keyPressHandler;
    private static WaveManager waveManager;
    private static UIController uiController;

    private Path path;
    private List<WayPoint> wayPoints;

    private long lastCall = 0,lastCall2 = 0;
    private final double fpsWanted = 60;
    private int frameCount;
    public static int MAXHP = -1, HP = MAXHP, FPS, totalGoldEarned, totalGoldSpend;
    private static int GOLD = 20;
    public static boolean isRunning, onPause;
    public static GameState state = GameState.VOID;

    @Override
    public void start(Stage stage) throws IOException {

        screenDim = Screen.getPrimary().getBounds();
        canvasSize = new Point2D(screenDim.getWidth(),screenDim.getHeight() -50);
        mainStage = stage;
        BorderPane bp = new BorderPane();

        canvas = new Canvas(screenDim.getWidth(),screenDim.getHeight());
        canvas.setScaleX(1);
        canvas.setScaleY(1);
        bp.setCenter(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();

        Scene scene = new Scene(bp, screenDim.getWidth(),screenDim.getHeight()-50);

        stage.setTitle("Σ Tower Defense Game");
        stage.setScene(scene);
        stage.show();

        path = new Path(11);
        wayPoints = path.getPoints();

        mouseHandler = new MouseHandler();
        uiController = new UIController(this);
        keyPressHandler = new KeyPressHandler(uiController);
        waveManager = new WaveManager(path);
        uiController.spawn();
        setState(GameState.START_MENU);

        scene.setOnMouseClicked(e -> mouseHandler.handle(e));
        scene.setOnMouseMoved(e -> mouseHandler.updateMousePos(e));
        scene.setOnKeyPressed(e -> keyPressHandler.handle(e));
        isRunning = true;
        instance = this;
    }

    private void update(){
            if (HP <= 0) {
                setState(GameState.GAME_OVER);
                onPause = true;
                //resetGameParams();
                isRunning = false;
            }

            render();

            if (!onPause) {
                if ((lastCall + (1_000_000_000 / fpsWanted)) <= System.nanoTime()) {
                    lastCall = System.nanoTime();
                    tick();
                    FPS++;
                }
            }
        frameCount++;

        if (lastCall2 + 1_000_000_000 <= System.nanoTime()){
            FPS = frameCount;
            frameCount = 0;
            lastCall2 = System.nanoTime();
        }

        cleanUp();
    }

    public static void onGameOver() {
        destroyAll();
        waveManager.destroy();
    }

    private static void destroyAll(){
        Tickable.newborn.clear();
        Tickable.expended.addAll(Tickable.active);
        Tickable.expended.clear();

        Renderable.newborn.clear();
        Renderable.expended.addAll(Renderable.active);
        Renderable.expended.clear();
        
    }

    private void tick(){
        for(Tickable t : Tickable.active){
            t.tick();
        }
    }

    private void render(){

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvasSize.getX(),canvasSize.getY());

        path.render(gc);

        renderHandler.render(gc);

        //uiController.render(gc);
    }

    private void cleanUp(){

        Clickable.active.addAll(Clickable.newborn);
        Clickable.active.removeAll(Clickable.expended);
        Clickable.expended.clear();
        Clickable.newborn.clear();

        ITower.active.addAll(ITower.newborn);
        ITower.active.removeAll(ITower.expended);
        ITower.expended.clear();
        ITower.newborn.clear();

        Tickable.active.addAll(Tickable.newborn);
        Tickable.active.removeAll(Tickable.expended);
        Tickable.newborn.clear();
        Tickable.expended.clear();

        IEnemy.active.addAll(IEnemy.newborn);
        IEnemy.active.removeAll(IEnemy.expended);
        IEnemy.newborn.clear();
        IEnemy.expended.clear();

    }

    private void resetGameParams(){
        HP = 10;
    }

    public static void main(String[] args) {
        launch();
    }

    public static void alterGoldAmount(int amount){
        if(amount <= 0){
            GOLD += amount;
            totalGoldSpend += amount;
        }else{
            GOLD += amount;
            totalGoldEarned += amount;
        }
    }
    public static int getGold(){
        return GOLD;
    }

    public static void onGameStart(){
        waveManager.spawn();
    }
    public static Main getInstance(){
        return instance;
    }

    public static void setState(GameState newState){
        if(state != newState) {
            System.out.println("Main.state changed to: " + state);
            state = newState;
            uiController.changeScene(state);
        }
    }
}