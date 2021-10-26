package com.Testing;

import java.awt.*;
import java.beans.IntrospectionException;

public class Game extends Canvas implements Runnable{

    public static boolean isRunning = false;
    private Thread renderThread, tickThread, interThread;
    private final GameWindow gameWindow;
    private final RenderHandler renderHandler;
    private final InteractionHandler interHandler;
    private static Graphics g;

    public static final int WIDTH = 1500;
    public static final int HEIGHT = 1000;
    double interpolation = 0;
    private final int TICKS_PER_SECOND = 60, SKIP_TICKS = 1000 / TICKS_PER_SECOND, MAX_FRAMESKIP = 5;

    public static void main(String[] args) {
        new Game();
    }

    public Game(){
        interHandler = new InteractionHandler();
        gameWindow = new GameWindow(WIDTH, HEIGHT, "MockUp", this);
        renderHandler = new RenderHandler(gameWindow);

        new Player(1,2);

        this.addKeyListener(new KeyInput(gameWindow));
        this.addMouseListener(new MouseInput(gameWindow));
    }

    @Override
    public void run() {
        this.requestFocus();

        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (isRunning){
            loops = 0;
            while(System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP){
                next_game_tick += SKIP_TICKS;
                loops++;

                for (Evaluatable t : Evaluatable.evaluatables){
                    t.Tick();
                }
            }
        }
        stop();
    }

    public synchronized void start() {
        tickThread = new Thread(this);
        renderThread = new Thread(renderHandler);
        interThread = new Thread(interHandler);

        renderThread.start();
        tickThread.start();
        interThread.start();

        isRunning = true;
    }

    public synchronized void stop(){
        try{
            renderThread.join();
            tickThread.join();
            interThread.join();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
