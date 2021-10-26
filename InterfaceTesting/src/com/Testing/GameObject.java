package com.Testing;

import java.awt.*;

public abstract class GameObject {
    protected int x,y;

    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point getPos(){
        return new Point(x,y);
    }
}
