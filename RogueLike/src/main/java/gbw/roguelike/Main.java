package gbw.roguelike;

import gbw.roguelike.handlers.*;
import gbw.roguelike.interfaces.Tickable;
import gbw.roguelike.ui.StartMenuScene;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
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
    public static double playerCanvasScaling = 2D;
    public static boolean onPause = false;
    public static int inGameFrameCounter = 0, uiFrameCounter = 0, inGameFPS = 0, uiFPS = 0;
    public static boolean playerIsDead = false;

    private static AnimationTimer inGameUpdates;
    private static AnimationTimer uiUpdates;
    private static Color tempColor = new Color(1,1,1,0.5);

    private long fpsCalc1 = 0, fpsCalc2 = 0, tickLastCall = 0, fpsWanted = 1_000_000_000 / 60;

    private Canvas canvasUI;
    private Canvas canvasGAME;
    private Canvas canvasPLAYER;
    private GraphicsContext gc1, gc2, gc3;

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

        mouseClickHandler = new MouseClickHandler();
        clickableManager = new ClickableManager();
        sceneManager = new SceneManager();
        SceneManager.changeScene(new StartMenuScene());
        damageInstanceManager = new DamageInstanceManager();
        localPlayer = new Player(new Point2D(0,0));

        keyPressHandler = new KeyPressHandler(localPlayer);
        keyReleasedHandler = new KeyReleasedHandler(localPlayer);

        worldSpace = new WorldSpace(localPlayer);
        gpg = new GamePathGenerator(worldSpace);

        Pane pane = new Pane();
        canvasUI = new Canvas(canvasDim.getX(),canvasDim.getY());
        canvasPLAYER = new Canvas(localPlayer.getRawSize().getX(), localPlayer.getRawSize().getY());
        canvasPLAYER.setLayoutX(Main.canvasDim.multiply(0.5).getX() - localPlayer.getSize().multiply(0.5).getX());
        canvasPLAYER.setLayoutY(Main.canvasDim.multiply(0.5).getY() - localPlayer.getSize().multiply(0.5).getY());
        canvasPLAYER.setScaleX(playerCanvasScaling);
        canvasPLAYER.setScaleY(playerCanvasScaling);
        canvasGAME = new Canvas(canvasDim.getX(),canvasDim.getY());

        pane.getChildren().add(canvasGAME);
        pane.getChildren().add(canvasPLAYER);
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
        gc3 = canvasPLAYER.getGraphicsContext2D();

        gc1.setFill(Color.BLACK);
        gc1.fillRect(0,0,canvasDim.getX(), canvasDim.getY());

        gc3.clearRect(0,0,canvasDim.getX(), canvasDim.getY());

        if(fpsCalc1 + 1_000_000_000 < System.nanoTime()){
            inGameFPS = inGameFrameCounter;
            inGameFrameCounter = 0;
            fpsCalc1 = System.nanoTime();
        }

        if(System.nanoTime() > tickLastCall + fpsWanted && !onPause){
            tick();
            damageInstanceManager.evaluate();
        }

        worldSpace.render(gc1);

        canvasPLAYER.setRotate(Player.facingDirection.inDegrees);
        localPlayer.render(gc3);

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

    public static void startUIUpdates(){
        uiUpdates.start();
    }

    public static void startInGameUpdates() {
        GamePathGenerator.setlevel(1);
        inGameUpdates.start();
    }

    public static void stopInGameUpdates(){
        inGameUpdates.stop();
    }

    public static void stopUIUpdates(){
        uiUpdates.stop();
    }

    public static void main(String[] args) {
        launch();
    }


    //TODO Fix idle animations for player movement
    //TODO Get that fcking level generator working
    //TODO Implement RoomCart.propegate() for generation instead. It works. Surprisingly.
    //TODO Lighting?
    //TODO weapon animation system
}