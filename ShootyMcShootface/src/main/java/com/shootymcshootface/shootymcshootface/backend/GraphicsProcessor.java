package com.shootymcshootface.shootymcshootface.backend;

import javafx.scene.image.Image;

public class GraphicsProcessor {

    private final String mainDirectory = "com/shootymcshootface/shootymcshootface";
    private final String graphicsDirectory = "/Graphics";
    private final String playerDirectory = "/Player";
    private final String backgroundDirectory = "/Background";

    public Image getBackground(){
        return new Image(getClass().getResourceAsStream(mainDirectory + graphicsDirectory + backgroundDirectory + "/DoomFlowerv5C-min.png"));
    }
    public Image playerImage(){
        return new Image(getClass().getResourceAsStream(mainDirectory + graphicsDirectory + playerDirectory + "/New Piskel-1.png.png"));
    }
}
