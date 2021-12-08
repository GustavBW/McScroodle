package com.shootymcshootface.shootymcshootface;

public class Game implements Runnable{

    public static double Height = 1000, Width = 1500;
    public static boolean isRunning = false, isWaiting = false;
    public static Game instance;

    private Thread renderThread, tickThread, interThread;
    private RenderHandler renderHandler;
    private CollisionHandler collisionHandler;

    public Game(){

        instance = this;
        collisionHandler = new CollisionHandler();
        renderHandler = new RenderHandler();
        start();
    }



    public void play(){





        stop();
    }


    public void start(){
        isRunning = true;

        interThread = new Thread(collisionHandler);

        tickThread = new Thread(this);
        renderThread = new Thread(renderHandler);

        interThread.start();
        tickThread.start();
        renderThread.start();
    }

    public synchronized void stop(){
        try{

            tickThread.join();
            interThread.join();
            renderThread.join();
            isRunning = false;
            System.exit(420);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Game();
    }

    @Override
    public void run() {
        isRunning = true;
        play();
    }
}
