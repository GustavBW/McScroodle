package com.shootymcshootface.shootymcshootface;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public interface Renderable {
    ArrayList<Renderable> renderables = new ArrayList<>();

    void render(GraphicsContext gc);
}
