package com.Testing;

import java.awt.*;

public class Player extends GameObject implements Evaluatable, Renderable{

    public Player(int x, int y){
        super(x,y);
        onInstancedEval();
    }



    @Override
    public void Tick() {
        System.out.println("TICK TOCK MOTHERFUCKER!! " + x + " " + y);
    }


    @Override
    public void Render(Graphics g) {
        System.out.println("I'M RENDERING YA'LL!! " + x + " " + y);
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
