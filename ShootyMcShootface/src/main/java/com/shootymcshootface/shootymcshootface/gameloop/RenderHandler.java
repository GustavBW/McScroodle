package com.shootymcshootface.shootymcshootface.gameloop;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RenderHandler extends Application implements Runnable{


    public static boolean isRunning, isReady = false, isWaiting = false;

    private GraphicsContext gc;
    private Canvas canvas;
    private Stage mainStage;
    private Scene mainScene;
    private Game game;

    @Override
    public void start(Stage stage) throws IOException {

        game = Game.instance;

        mainStage = stage;
        mainStage.setOnCloseRequest(e -> stop());

        BorderPane bp = new BorderPane();
        canvas = new Canvas(Game.Width,Game.Height);
        gc = canvas.getGraphicsContext2D();
        bp.setCenter(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!Game.isRunning){
                    stop();
                }
                onUpdate(canvas.getGraphicsContext2D());
            }
        };
        timer.start();

        mainScene = new Scene(bp, Game.Width,Game.Height);

        stage.setTitle("Shooty Mc Shootface");
        stage.setScene(mainScene);
        stage.show();
        isReady = true;
    }


    public void onUpdate(GraphicsContext gc){

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,Game.Width,Game.Height);

    }



    public synchronized void stop(){
        System.out.println("RenderHandler.Stop() called");
        isRunning = false;
        mainStage.close();
        System.exit(69);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void run() {
        isRunning = true;
        String[] args = new String[]{}; //Tricking the Application to launch even though this is not initiated by the System thread.
        launch(args);
    }
}