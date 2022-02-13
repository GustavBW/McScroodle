package gbw.tdg.towerdefensegame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public static Point2D canvasSize = new Point2D(2000,1500);
    private Canvas canvas;
    private Stage mainStage;
    private Rectangle2D screenDim;
    private Random random;
    private GraphicsContext gc;
    private MouseHandler mouseHandler;
    private UIController uiController;

    private Path path;
    private ArrayList<WayPoint> wayPoints;

    private long lastCall = 0, lastCall2 = 0;
    private final double fpsWanted = 60;
    private final int numOfWayPoints = 11;
    public static int HP = -1, MAXHP = 10;
    public static boolean isRunning, onPause;

    private ArrayList<Enemy> enemies;
    public static ArrayList<Enemy> removeEnemy = new ArrayList<>();
    private ArrayList<Clickable> clickables;
    public static ArrayList<Clickable> removeClickable = new ArrayList<>();
    public static ArrayList<Clickable> addClickable = new ArrayList<>();
    private ArrayList<Tower> towers;
    public static ArrayList<Tower> removeTower = new ArrayList<>();
    public static ArrayList<Tower> addTower = new ArrayList<>();
    private ArrayList<DummyBullet> projectiles;
    public static ArrayList<DummyBullet> addProjectile = new ArrayList<>();
    public static ArrayList<DummyBullet> removeProjectile = new ArrayList<>();
    private ArrayList<Tickable> tickables;
    public static ArrayList<Tickable> addTickable = new ArrayList<>();
    public static ArrayList<Tickable> removeTickable = new ArrayList<>();


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

        stage.setTitle("Tower Defense Game");
        stage.setScene(scene);
        stage.show();

        random = new Random();

        path = new Path(createWayPoints());

        clickables = new ArrayList<>();
        mouseHandler = new MouseHandler(clickables);
        uiController = new UIController(this);

        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
        tickables = new ArrayList<>();
        towers.add(new Tower(new Point2D(500,500), 40, 300, 1.00, this));

        scene.setOnMouseClicked(e -> mouseHandler.handle(e));
        isRunning = true;
    }

    private void update(){

        if(HP <= -1){
            uiController.showGameOver();
            isRunning = false;
        }

        render();

        if(isRunning) {
            if (!onPause) {
                if ((lastCall + (1_000_000_000 / fpsWanted)) <= System.nanoTime()) {
                    lastCall = System.nanoTime();
                    tick();
                }

                cleanUp();
            }
        }
    }

    private void tick(){
        if(lastCall2 + 2_000 < System.currentTimeMillis()){
            enemies.add(new Enemy(path.getStart().x,path.getStart().y,path));
            lastCall2 = System.currentTimeMillis();
        }

        for(Tickable t : tickables){
            t.tick();
        }

    }

    private void render(){

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvasSize.getX(),canvasSize.getY());

        path.render(gc);

        for(WayPoint p : wayPoints){
            p.render(gc);
        }

        for(Enemy e : enemies){
            e.render(gc);
        }

        for(Tower t : towers){
            t.render(gc);
        }

        for(DummyBullet d : projectiles){
            d.render(gc);
        }

        uiController.render(gc);
    }

    private void cleanUp(){
        enemies.removeAll(removeEnemy);
        removeEnemy.clear();

        clickables.removeAll(removeClickable);
        removeClickable.clear();
        clickables.addAll(addClickable);
        addClickable.clear();

        towers.removeAll(removeTower);
        removeTower.clear();
        towers.addAll(addTower);
        addTower.clear();

        projectiles.removeAll(removeProjectile);
        removeProjectile.clear();
        projectiles.addAll(addProjectile);
        addProjectile.clear();

        tickables.removeAll(removeTickable);
        removeTickable.clear();
        tickables.addAll(addTickable);
        addTickable.clear();
    }

    private ArrayList<WayPoint> createWayPoints(){
        wayPoints = new ArrayList<>();
        wayPoints.add(new WayPoint(0,0,1));

        ArrayList<WayPoint> list = new ArrayList<>();
        for(int i = 2; i < numOfWayPoints + 1; i++){
            list.add(new WayPoint(random.nextInt((int) canvasSize.getX()), random.nextInt((int) canvasSize.getY()), i));
        }

        wayPoints.addAll(list);
        wayPoints.add(new WayPoint(canvasSize.getX()* 0.9,  canvasSize.getY() * 0.9,numOfWayPoints));

        return wayPoints;
    }

    public static void main(String[] args) {
        launch();
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }
}