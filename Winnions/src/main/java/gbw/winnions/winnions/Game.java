package gbw.winnions.winnions;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Game extends Application {

    public static final Point2D gameDimensions = new Point2D(1500,1000);
    public static boolean isRunning = false;
    public static Player localPlayer;
    public static PlayerCamera localPlayerCamera;

    private Stage mainStage;
    private GraphicsContext gc;
    private Canvas canvas;
    private WorldSpace worldSpace;
    private TickHandler tickHandler;
    private CollisionHandler colHandler;

    private Thread tickThread;
    private Thread colThread;

    private KeyPressHandler keyPressHandler;
    private KeyReleaseHandler keyReleaseHandler;
    private MouseHandler mouseHandler;

    @Override
    public void start(Stage stage) throws IOException {

        mainStage = stage;
        mainStage.setResizable(false);
        mainStage.setTitle("Winnions");
        mainStage.setOnCloseRequest(this::stop);
        mainStage.requestFocus();

        worldSpace = new WorldSpace();
        tickHandler = new TickHandler();

        localPlayer = new Player(new Point2D(500,500),1);
        localPlayerCamera = new PlayerCamera(localPlayer);
        keyPressHandler = new KeyPressHandler(localPlayer, localPlayerCamera);
        keyReleaseHandler = new KeyReleaseHandler(localPlayer, localPlayerCamera);
        mouseHandler = new MouseHandler(localPlayer, localPlayerCamera);

        colHandler = new CollisionHandler(localPlayer);

        WorldSpace.addRenderable(localPlayer, LayerType.Middleground0);
        TickHandler.addTickable(localPlayer);
        TickHandler.addTickable(localPlayerCamera);

        canvas = new Canvas((int) gameDimensions.getX(), (int) gameDimensions.getY());
        gc = canvas.getGraphicsContext2D();

        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderFrame(); //Calling the render pipeline below every time Application throws an event of this type.
            }
        };
        timer.start();

        Scene scene = new Scene(bp, gameDimensions.getX(),gameDimensions.getY());

        scene.setOnKeyPressed(e -> keyPressHandler.handle(e));
        scene.setOnKeyReleased(e -> keyReleaseHandler.handle(e));
        scene.setOnMouseClicked(e -> mouseHandler.handle(e));

        stage.setScene(scene);
        stage.show();
        isRunning = true;
        startThreads();
    }


    private void renderFrame(){

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,gameDimensions.getX(),gameDimensions.getY());

        worldSpace.render(gc, null);
    }


    public synchronized void startThreads(){
        tickThread = new Thread(tickHandler);
        colThread = new Thread(colHandler);

        tickThread.start();
        colThread.start();
    }
    public synchronized void stop(WindowEvent we){
        isRunning = false;

        try{
            tickThread.join();
            colThread.join();
        }catch(Exception e){
            e.printStackTrace();
        }

        System.exit(69);
    }
    public static void main(String[] args) {
        launch();
    }
}