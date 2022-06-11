package gbw.circlerenderer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static double[] canvasDim = new double[]{1000,500};
    private Canvas canvas;

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        canvas = new Canvas(canvasDim[0],canvasDim[1]);
        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane, canvasDim[0],canvasDim[1]);
        stage.setTitle("CircleRenderer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}