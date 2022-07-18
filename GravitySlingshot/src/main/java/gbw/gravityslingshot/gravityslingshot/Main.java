package gbw.gravityslingshot.gravityslingshot;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    public static Point2D dim = new Point2D(1500,1000);
    public static Random rand = new Random(System.currentTimeMillis());
    public static AnimationTimer inGameTimer;

    private GravityHandler gravityHandler;
    private CollisionHandler collisionHandler;
    private KeyInputHandler keyInputHandler;
    private MouseInputHandler mouseInputHandler;
    private PathTracer pathTracer;

    private Canvas mainCanvas;
    private Canvas backgroundCanvas;
    private Pane mainPane;
    private Stage mainStage;
    private GraphicsContext gc;

    private int fpsWanted = 60;
    private long lastFrameCall;


    @Override
    public void start(Stage stage) throws IOException {
        mouseInputHandler = new MouseInputHandler();
        keyInputHandler = new KeyInputHandler();

        mainCanvas = new Canvas(dim.getX(),dim.getY());
        backgroundCanvas = new Canvas(dim.getX(),dim.getY());
        mainCanvas.setOnMouseMoved(e -> mouseInputHandler.mouseMoved(e));
        mainPane = new Pane();
        mainStage = stage;
        mainPane.getChildren().addAll(
                backgroundCanvas,
                mainCanvas
        );
        Scene scene = new Scene(mainPane, dim.getX(),dim.getY());
        scene.setOnKeyPressed(e -> keyInputHandler.keyPress(e));
        scene.setOnKeyReleased(e -> keyInputHandler.keyReleased(e));

        pathTracer = new PathTracer(backgroundCanvas);

        inGameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        generateLevel();

        mainStage.setTitle("Gravity Slingshot");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void update(){
        gc = mainCanvas.getGraphicsContext2D();

        if(System.nanoTime() >= lastFrameCall + (fpsWanted * 1_000_000L)){
            tick();
        }

        render(gc);
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0,0,dim.getX(),dim.getY());

        for(Renderable r : Renderable.active){
            r.render(gc);
        }

        Renderable.active.removeAll(Renderable.expended);
        Renderable.active.addAll(Renderable.newborn);
        Renderable.expended.clear();
        Renderable.newborn.clear();
    }

    private void tick(){
        for(Tickable t : Tickable.active){
            t.tick();
        }
        Tickable.active.removeAll(Tickable.expended);
        Tickable.active.addAll(Tickable.newborn);
        Tickable.expended.clear();
        Tickable.newborn.clear();
    }

    private void generateLevel(){
        if(inGameTimer != null) {
            inGameTimer.stop();
            System.out.println("Stopped inGameTimer");
        }
        System.out.println("Generating level");
        List<IGameObject> level = new ArrayList<>(List.of(
            new GravityHandler(),
            new CollisionHandler(),
            new Cannon(0, dim.multiply(0.5))
        ));

        List<GravityObject> tracables = new ArrayList<>();
        for(int i =  0; i < 10; i++){
            Point2D pos = new Point2D(rand.nextDouble() * dim.getX(), rand.nextDouble() * dim.getY());
            GravityObject g = new GravityObject(10,
                    pos,
                    new Orbit(pos,10 + rand.nextDouble() * (.3 * dim.getX()),(fpsWanted * 3) + (rand.nextDouble() * 60)));
            level.add(g);
            tracables.add(g);
        }
        pathTracer.traceLevel(tracables);

        System.out.println("Spawning objects");
        for(IGameObject g : level){
            g.spawn();
        }

        if(inGameTimer != null) {
            inGameTimer.start();
            System.out.println("Started IGTimer");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}