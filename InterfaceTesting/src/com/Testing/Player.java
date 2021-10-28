package com.Testing;

import java.awt.*;

public class Player extends GameObject implements Evaluatable, Renderable{

    private double velX = 0.0D, velY = 0.0D, drag = 0.9;

    public Player(int x, int y){
        super(x,y);

        onInstancedEval();
        onInstancedRend();
    }



    @Override
    public void Tick() {
        x += velX;
        y += velY;



        velX *= drag;
        velY *= drag;
    }

    @Override
    public void Render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, 32, 32);
    }


    public void changeVelX(double change){
        velX += change;
    }
    public void changeVelY(double change){
        velY += change;
    }

    @Override
    public void onInstancedRend() {
        Renderable.renderables.add(this);
    }
    @Override
    public void onInstancedEval() {
        Evaluatable.evaluatables.add(this);
    }
}
