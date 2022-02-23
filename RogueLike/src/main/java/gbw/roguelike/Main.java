package gbw.roguelike;

import gbw.roguelike.handlers.*;
import gbw.roguelike.interfaces.Tickable;
import gbw.roguelike.ui.StartMenuScene;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Point2D canvasDim = new Point2D(1000,1000);
    public static Point2D sceneDim = new Point2D(1000,1000);
    public static AnimationTimer inGameUpdates;
    public static AnimationTimer uiUpdates;
    public static boolean onPause = false;

    public static int inGameFrameCounter = 0, uiFrameCounter = 0, inGameFPS = 0, uiFPS = 0;
    private long fpsCalc1 = 0, fpsCalc2 = 0, tickLastCall = 0, fpsWanted = 1_000_000_000 / 60;

    private Canvas canvasUI;
    private Canvas canvasGAME;
    private GraphicsContext gc1, gc2;

    private GamePathGenerator gpg;
    private WorldSpace worldSpace;
    private MouseClickHandler mouseClickHandler;
    private ClickableManager clickableManager;
    private SceneManager sceneManager;
    private DamageInstanceManager damageInstanceManager;
    private Player localPlayer;

    private KeyPressHandler keyPressHandler;
    private KeyReleasedHandler keyReleasedHandler;

    @Override
    public void start(Stage stage) throws IOException {

        gpg = new GamePathGenerator();
        mouseClickHandler = new MouseClickHandler();
        clickableManager = new ClickableManager();
        sceneManager = new SceneManager();
        SceneManager.changeScene(new StartMenuScene());
        damageInstanceManager = new DamageInstanceManager();
        localPlayer = new Player(new Point2D(canvasDim.getX(),canvasDim.getY()));

        keyPressHandler = new KeyPressHandler();
        keyReleasedHandler = new KeyReleasedHandler();

        worldSpace = new WorldSpace();
        worldSpace.addRoom(gpg.getStartingRoom());

        Pane pane = new Pane();
        canvasUI = new Canvas(canvasDim.getX(),canvasDim.getY());
        canvasGAME = new Canvas(canvasDim.getX(),canvasDim.getY());
        pane.getChildren().add(canvasGAME);
        pane.getChildren().add(canvasUI);

        inGameUpdates = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateInGame();
            }
        };
        uiUpdates = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateUI();
            }
        };
        uiUpdates.start();

        Scene scene = new Scene(pane, sceneDim.getX(), sceneDim.getY());

        scene.setOnMouseClicked(e -> mouseClickHandler.handle(e));

        scene.setOnKeyPressed(e -> keyPressHandler.handle(e));
        scene.setOnKeyReleased(e -> keyReleasedHandler.handle(e));

        stage.setTitle("Some Rogue-Like");
        stage.setScene(scene);
        stage.show();
    }

    private void updateInGame(){
        gc1 = canvasGAME.getGraphicsContext2D();
        gc1.setFill(Color.BLACK);
        gc1.fillRect(0,0,canvasDim.getX(), canvasDim.getY());

        if(fpsCalc1 + 1_000_000_000 < System.nanoTime()){
            inGameFPS = inGameFrameCounter;
            inGameFrameCounter = 0;
            fpsCalc1 = System.nanoTime();
        }

        if(System.nanoTime() > tickLastCall + fpsWanted){
            tick();
            damageInstanceManager.evaluate();
        }

        worldSpace.render(gc1);

        inGameFrameCounter++;
    }

    private void updateUI(){
        gc2 = canvasUI.getGraphicsContext2D();
        gc2.clearRect(0,0, canvasDim.getX(),canvasDim.getY());

        if(fpsCalc2 + 1_000_000_000 < System.nanoTime()){
            uiFPS = uiFrameCounter;
            uiFrameCounter = 0;
            fpsCalc2 = System.nanoTime();
        }

        sceneManager.render(gc2);

        uiFrameCounter++;
    }

    private void tick(){
        for(Tickable t : Tickable.tickables){
            t.tick();
        }
        worldSpace.evaluateWhichRoomsAreVisible();
    }

    public static void main(String[] args) {
        launch();
    }
}