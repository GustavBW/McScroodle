package com.Testing;

import com.Testing.Interfaces.Evaluatable;

import javax.swing.*;
import java.awt.*;

public class Game extends Canvas implements Runnable{
    public static boolean isRunning = false;
    private Thread renderThread, tickThread, interThread;
    private final GameWindow gameWindow;
    private RenderHandler renderHandler;
    private InteractionHandler interactionHandler;
    private static Graphics g;
    public static Game instance;
    private static JFrame frame;
    private final Player player;

    public static final int WIDTH = 1500, HEIGHT = 1000;
    double interpolation = 0;
    private final int TICKS_PER_SECOND = 60, SKIP_TICKS = 1000 / TICKS_PER_SECOND, MAX_FRAMESKIP = 5;

    public static void main(String[] args) {
        instance = new Game();
    }

    public Game(){
        frame = new JFrame("TestTitle");

        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(this);
        gameWindow = new GameWindow(WIDTH, HEIGHT, "MockUp", this);

        frame.requestFocus();
        frame.setEnabled(true);

        player = new Player(WIDTH / 2,HEIGHT / 2);

        interactionHandler = new InteractionHandler();
        this.setName("aComponents");

        renderHandler = new RenderHandler(this);
        renderThread = new Thread(renderHandler);
        renderThread.start();

        this.addKeyListener(new KeyInput(gameWindow, player));
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
        interThread = new Thread(interactionHandler);
        System.out.println("Threads instatiated");

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

    public GameWindow getGameWindow(){return gameWindow;}


    public Point getScreenDimensions(){
        return new Point(WIDTH, HEIGHT);
    }

}
