package gbw.roguelike;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Point2D canvasDim = new Point2D(1000,1000);
    public static Point2D sceneDim = new Point2D(1000,1000);

    private Canvas canvas;

    private GamePathGenerator gpg;
    private WorldSpace worldSpace;

    @Override
    public void start(Stage stage) throws IOException {

        gpg = new GamePathGenerator();
        worldSpace = new WorldSpace();
        worldSpace.addRoom(gpg.getStartingRoom());

        BorderPane bp = new BorderPane();
        canvas = new Canvas(canvasDim.getX(),canvasDim.getY());
        bp.setCenter(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();

        Scene scene = new Scene(bp, sceneDim.getX(), sceneDim.getY());
        stage.setTitle("Some Rogue-Like");
        stage.setScene(scene);
        stage.show();
    }

    private void update(){
        System.out.println("YOOO WE RUNNING BOIS");
        tick();
        worldSpace.render(canvas.getGraphicsContext2D());
    }

    private void tick(){
        worldSpace.evaluateWhichRoomsAreVisible();
    }

    public static void main(String[] args) {
        launch();
    }
}