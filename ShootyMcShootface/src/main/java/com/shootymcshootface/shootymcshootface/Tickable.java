package com.shootymcshootface.shootymcshootface;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public interface Tickable {

    ArrayList<Tickable> tickables = new ArrayList<>();

    void tick();
    Point2D getPosition();

}
