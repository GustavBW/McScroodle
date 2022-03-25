package gbw.gravityslingshot.gravityslingshot;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Canvas mainCanvas;
    private Pane mainPane;
    private Stage mainStage;
    private GraphicsContext gc;

    private int fpsWanted = 60;
    private long lastFrameCall;


    @Override
    public void start(Stage stage) throws IOException {
        mainCanvas = new Canvas();
        mainPane = new Pane();
        mainStage = stage;
        mainPane.getChildren().add(mainCanvas);
        Scene scene = new Scene(mainPane, 320, 240);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();

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




        Renderable.active.removeAll(Renderable.expended);
        Renderable.active.addAll(Renderable.newborn);
        Renderable.expended.clear();
        Renderable.newborn.clear();
    }

    private void tick(){



        Tickable.active.removeAll(Tickable.expended);
        Tickable.active.addAll(Tickable.newborn);
        Tickable.expended.clear();
        Tickable.newborn.clear();
    }

    public static void main(String[] args) {
        launch();
    }
}