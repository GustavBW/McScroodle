package com.testing.shadersandstuff;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Stage mainStage;
    private Canvas canvas;
    private GraphicsContext gc;
    private Pixel[][] pixelArray;
    public static int frames = 0;
    private int frametime = 0;
    private RenderableText frameText;
    private Random random;
    private Point[] voronoiPoints;
    private KeyHandler keyHandler;
    private ArrayList<Texture> textureArray;

    public static Point2D canvasDim = new Point2D(500,500);
    public static Point2D sceneDim = new Point2D(1000,750);
    public static boolean pause = false;

    @Override
    public void start(Stage stage) throws IOException {
        keyHandler = new KeyHandler();
        mainStage = stage;
        mainStage.setTitle("Testing Shaders");


        BorderPane bp = new BorderPane();
        canvas = new Canvas(canvasDim.getX(),canvasDim.getY());
        canvas.setScaleX((sceneDim.getX() / canvasDim.getX()));
        canvas.setScaleY((sceneDim.getY() / canvasDim.getY()));

        random = new Random();
        pixelArray = fillPixels();
        textureArray = evaluateTextureArray();
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
        scene.setOnKeyPressed(e -> keyHandler.handle(e));
        stage.setTitle("Testing Shaders");
        stage.setScene(scene);
        stage.show();
    }


    private void onUpdate(){
        long timeA = System.nanoTime();
        long timeB = 10000000;
        if(!pause) {
            evaluatePixels();
            updateUI();
            render();
            frames++;
            timeB = System.nanoTime();
            frametime = (int) (1 / (((timeB - timeA) / 1000) + 1));
        }
    }

    private ArrayList<Texture> evaluateTextureArray(){
        ArrayList<Texture> array = new ArrayList<>();

        array.add(new Voronoi(100,5));

        return array;
    }

    private void evaluatePixels(){

        for(int y = 0; y < canvasDim.getY(); y++){

            for(int x = 0; x < canvasDim.getX(); x++){

                for(Texture t : textureArray) {
                    t.computePixel(pixelArray[x][y]);
                }
            }
        }
    }

    private void updateUI(){

        frameText.setText(String.valueOf(frames) + " (" + frametime + ")");

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

    private Point[] calculateVoronoiPoints(int amount){

        Point[] points = new Point[amount];

        for(int i = 0; i < amount; i++){
            points[i] = new Point(random.nextInt(0,(int) canvasDim.getX()), random.nextInt(0,(int) canvasDim.getY()));
            System.out.println("VPoint made at: " + points[i].x + " | " + points[i].y);
        }

        return points;
    }

    public static void main(String[] args) {
        launch();
    }
}