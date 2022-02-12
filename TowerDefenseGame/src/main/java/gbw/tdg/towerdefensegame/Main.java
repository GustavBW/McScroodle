package gbw.tdg.towerdefensegame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Canvas canvas;
    private Stage mainStage;
    private Rectangle2D screenDim;
    private Point2D canvasSize;
    private Random random;
    private GraphicsContext gc;

    private Path path;
    private ArrayList<WayPoint> wayPoints;
    private ArrayList<Enemy> enemies;

    private double lastCall = 0, lastCall2 = 0;
    private final double fpsWanted = 60;



    @Override
    public void start(Stage stage) throws IOException {

        screenDim = Screen.getPrimary().getBounds();

        mainStage = stage;
        BorderPane bp = new BorderPane();

        canvasSize = new Point2D(1000,1000);
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

        Scene scene = new Scene(bp, 1000,1000);
        stage.setTitle("Tower Defense Game");
        stage.setScene(scene);
        stage.show();

        random = new Random();
        ArrayList<WayPoint> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(new WayPoint(random.nextInt((int) canvasSize.getX()), random.nextInt((int) canvasSize.getY())));
        }

        wayPoints = new ArrayList<>();
        wayPoints.addAll(list);
        wayPoints.add(new WayPoint(0,0));
        wayPoints.add(new WayPoint(canvasSize.getX()* 0.9,  canvasSize.getY() * 0.9));
        path = new Path(list, new WayPoint(0,0), new WayPoint(canvasSize.getX()* 0.9,  canvasSize.getY() * 0.9));

        enemies = new ArrayList<>();
    }

    private void update(){

        render();

        if((lastCall + (1_000_000_000 / fpsWanted)) <= System.nanoTime()){
            lastCall = System.nanoTime();
            tick();
        }

    }

    private void tick(){
        System.out.println("tick");
        if(lastCall2 + 2_000_000_000 < System.nanoTime()){
            enemies.add(new Enemy(path.getStart().x,path.getStart().y,path));
            System.out.println("Enemy spawned");
            lastCall2 = System.nanoTime();
        }

        for(Enemy e : enemies){
            e.tick();
        }

    }

    private void render(){

        gc = canvas.getGraphicsContext2D();

        path.render(gc);

        for(WayPoint p : wayPoints){
            p.render(gc);
        }

        for(Enemy e: enemies){
            e.render(gc);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}