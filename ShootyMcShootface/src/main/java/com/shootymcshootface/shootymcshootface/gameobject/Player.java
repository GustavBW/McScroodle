package com.shootymcshootface.shootymcshootface.gameobject;

import com.shootymcshootface.shootymcshootface.backend.ContentEngine;
import com.shootymcshootface.shootymcshootface.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Player extends GameObject implements Tickable {

    private double velX, velY, posX, posY;
    private Image image;

    public Player(double x, double y, GameObjectType type) {
        super(x, y, type);
        this.posX = x;
        this.posY = y;

        image = ContentEngine.getPlayerImage();
    }


    @Override
    public void tick() {




    }

    @Override
    public Point2D getPosition() {
        return new Point2D(posX, posY);
    }
}
