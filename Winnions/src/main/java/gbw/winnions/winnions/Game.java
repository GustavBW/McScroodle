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
    public static List<Renderable> junk = new ArrayList<>();
    private static List<Renderable> removeNextPass = new ArrayList<>();
    private static List<Renderable> addNextPass = new ArrayList<>();

    private Stage mainStage;
    private GraphicsContext gc;
    private Canvas canvas;
    private WorldSpace worldSpace;
    private TickHandler tickHandler;

    private Thread tickThread;

    private Player player1;
    private PlayerCamera p1Camera;
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

        player1 = new Player(new Point2D(500,500),1);
        p1Camera = new PlayerCamera(player1, new Point2D(0,0));
        keyPressHandler = new KeyPressHandler(player1, p1Camera);
        keyReleaseHandler = new KeyReleaseHandler(player1, p1Camera);
        mouseHandler = new MouseHandler(player1, p1Camera);

        TickHandler.addTickable(player1);

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

        worldSpace.render(gc);
        player1.render(gc);

        for(Renderable r : junk){
            r.render(gc);
        }

        updateJunkList();
    }

    private void updateJunkList(){
        junk.addAll(addNextPass);
        junk.removeAll(removeNextPass);

        for(int i = 0; i < 3; i++){
            try {
                removeNextPass.clear();
                addNextPass.clear();
                break;
            } catch (ConcurrentModificationException e) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void addRenderable(Renderable r){
        addNextPass.add(r);
    }
    public static void removeRenderable(Renderable r){
        removeNextPass.add(r);
    }


    public synchronized void startThreads(){
        tickThread = new Thread(tickHandler);
        tickThread.start();
    }
    public synchronized void stop(WindowEvent we){
        isRunning = false;

        try{
            tickThread.join();
        }catch(Exception e){
            e.printStackTrace();
        }

        System.exit(69);
    }
    public static void main(String[] args) {
        launch();
    }
}