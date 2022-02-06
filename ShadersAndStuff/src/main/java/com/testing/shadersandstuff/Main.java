package com.testing.shadersandstuff;

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

import java.io.IOException;

public class Main extends Application {

    private Stage mainStage;
    private Canvas canvas;
    private GraphicsContext gc;
    private Pixel[][] pixelArray;
    private int frames = 0;
    private RenderableText frameText;

    public static Point2D canvasDim = new Point2D(255,255);
    public static Point2D sceneDim = new Point2D(2000,1500);

    @Override
    public void start(Stage stage) throws IOException {

        mainStage = stage;
        mainStage.setTitle("Testing Shaders");

        BorderPane bp = new BorderPane();
        canvas = new Canvas(canvasDim.getX(),canvasDim.getY());
        canvas.setScaleX((sceneDim.getX() / canvasDim.getX()));
        canvas.setScaleY((sceneDim.getY() / canvasDim.getY()));

        pixelArray = fillPixels();
        frameText = new RenderableText(new Point2D(5,15),"0");
        gc = canvas.getGraphicsContext2D();

        bp.setCenter(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
               onUpdate(); //Calling the render pipeline below every time Application throws an event of this type.
            }
        };
        timer.start();

        Scene scene = new Scene(bp,sceneDim.getX(),sceneDim.getY());
        stage.setTitle("Testing Shaders");
        stage.setScene(scene);
        stage.show();
    }


    private void onUpdate(){
        evaluatePixels();
        updateUI();
        render();
        frames++;
    }

    private void evaluatePixels(){

        for(int y = 0; y < canvasDim.getY(); y++){

            for(int x = 0; x < canvasDim.getX(); x++){

                pixelArray[x][y].setColor(0.5D * (x * x) +  0.5D * (y * y) , 0, 0.5D * (x * x) +  0.5D * (y * y) + frames);

            }

        }
    }

    private void updateUI(){

        frameText.setText(String.valueOf(frames));

    }

    private void render(){
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,1000,1000);

        for(Pixel[] pA : pixelArray){

            for(Pixel p : pA){
                p.render(gc);
            }

        }

        frameText.render(gc);
    }

    private Pixel[][] fillPixels(){
        System.out.println("Filling pixels: ");
        Pixel[][] pixelArray = new Pixel[(int) canvasDim.getX()][(int) canvasDim.getY()];

        for(int y = 0; y < canvasDim.getY(); y++){

            for(int x = 0; x < canvasDim.getX(); x++){

                pixelArray[x][y] = new Pixel(0,0,255,x,y);

            }

            System.out.print("|");
        }

        System.out.println("");
        System.out.println("Done");
        return pixelArray;
    }

    public static void main(String[] args) {
        launch();
    }
}