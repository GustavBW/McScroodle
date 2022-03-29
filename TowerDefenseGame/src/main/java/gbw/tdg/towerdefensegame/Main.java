package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.UIController;
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

    public static Point2D canvasSize = new Point2D(2000,1500);
    public static Random random = new Random(System.currentTimeMillis());
    private Canvas canvas;
    private Stage mainStage;
    private Rectangle2D screenDim;
    private GraphicsContext gc;
    private MouseHandler mouseHandler;
    private KeyPressHandler keyPressHandler;
    private static UIController uiController;

    private Path path;
    private List<WayPoint> wayPoints;

    private long lastCall = 0, lastCall2 = 0;
    private final double fpsWanted = 60;
    public static int HP = 10, MAXHP = 10;
    public static boolean isRunning, onPause;
    public static GameState state = GameState.VOID;

    private ArrayList<Enemy> enemies;
    public static ArrayList<Enemy> removeEnemy = new ArrayList<>();

    private ArrayList<DummyBullet> projectiles;
    public static ArrayList<DummyBullet> addProjectile = new ArrayList<>();
    public static ArrayList<DummyBullet> removeProjectile = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {

        screenDim = Screen.getPrimary().getBounds();

        mainStage = stage;
        BorderPane bp = new BorderPane();

        canvas = new Canvas(canvasSize.getX(),canvasSize.getY());
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

        Scene scene = new Scene(bp, canvasSize.getX(),canvasSize.getY());

        stage.setTitle("Σ Tower Defense Game");
        stage.setScene(scene);
        stage.show();

        path = new Path(11);
        wayPoints = path.getPoints();

        mouseHandler = new MouseHandler();
        keyPressHandler = new KeyPressHandler();
        uiController = new UIController(this);
        uiController.spawn();
        setState(GameState.START_MENU);

        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        Tower newTower = new Tower(new Point2D(500,500), 40, 500, 1.00, this);
        newTower.spawn();

        scene.setOnMouseClicked(e -> mouseHandler.handle(e));
        scene.setOnKeyPressed(e -> keyPressHandler.handle(e));
        isRunning = true;
    }

    private void update(){

            if (HP <= 0) {
                state = GameState.GAME_OVER;
                //resetGameParams();
                isRunning = false;
            }

            render();

            if (!onPause) {
                if ((lastCall + (1_000_000_000 / fpsWanted)) <= System.nanoTime()) {
                    lastCall = System.nanoTime();
                    tick();
                }
            }


        cleanUp();
    }

    private void tick(){
        if(lastCall2 + 500 < System.currentTimeMillis()){
            Enemy newEnemy = new Enemy(path.getStartPoint().x,path.getStartPoint().y,path);
            newEnemy.spawn();
            lastCall2 = System.currentTimeMillis();
        }

        for(Tickable t : Tickable.active){
            t.tick();
        }

    }

    private void render(){

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvasSize.getX(),canvasSize.getY());

        path.render(gc);

        for(Renderable r : Renderable.active){
            r.render(gc);
        }

        uiController.render(gc);
    }

    private void cleanUp(){

        enemies.removeAll(removeEnemy);
        removeEnemy.clear();

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

        Renderable.active.addAll(Renderable.newborn);
        Renderable.active.removeAll(Renderable.expended);
        Renderable.newborn.clear();
        Renderable.expended.clear();

        projectiles.removeAll(removeProjectile);
        removeProjectile.clear();
        projectiles.addAll(addProjectile);
        addProjectile.clear();
    }

    private void resetGameParams(){
        HP = 10;
    }

    public static void main(String[] args) {
        launch();
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }
    public static void setState(GameState newState){
        if(state != newState) {
            System.out.println("Main.state changed to: " + state);
            state = newState;
            uiController.changeScene(state);
        }
    }
}