package com.shootymcshootface.shootymcshootface;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private GraphicsContext gc;
    private Canvas canvas;
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {

        BorderPane bp = new BorderPane();
        mainStage = stage;
        canvas = new Canvas(Game.Width,Game.Height);
        gc = canvas.getGraphicsContext2D();
        bp.setCenter(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!Game.isRunning){
                    stop();
                }
                onUpdate(); //Calling the render pipeline below every time Application throws an event of this type.
            }
        };
        timer.start();

        Scene scene = new Scene(bp, Game.Width,Game.Height);
        stage.setTitle("Shooty Mc Shootface");
        stage.setScene(scene);
        stage.show();
    }


    public void onUpdate(){


    }


    public static void main(String[] args) {
        launch();
    }
}