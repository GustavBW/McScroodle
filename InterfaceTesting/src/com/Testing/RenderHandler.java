package com.Testing;

import com.Testing.Interfaces.Renderable;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class RenderHandler extends Canvas implements Runnable{

    private Canvas gameWindow;
    private Graphics g;
    double interpolation = 0;
    private final int TICKS_PER_SECOND = 60, SKIP_TICKS = 1000 / TICKS_PER_SECOND, MAX_FRAMESKIP = 5;

    public RenderHandler(Canvas gameWindow){
        this.gameWindow = gameWindow;
    }

    @Override
    public synchronized void run() {
        int loops = 0;
        double next_game_tick = System.currentTimeMillis();

        while(Game.isRunning){
            loops = 0;

            while(System.currentTimeMillis() < next_game_tick && loops < MAX_FRAMESKIP){
                next_game_tick += SKIP_TICKS;
                loops++;

            }
            Render();
        }
    }
    public void Render(){

        BufferStrategy bs;
        bs = gameWindow.getBufferStrategy();
        if (bs == null){
            this.gameWindow.createBufferStrategy(2);
            bs = gameWindow.getBufferStrategy();
        }

        g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,gameWindow.getWidth(),gameWindow.getHeight());

        for(Renderable r : Renderable.renderables){
            r.Render(g);
        }

        bs.show();
    }
}
