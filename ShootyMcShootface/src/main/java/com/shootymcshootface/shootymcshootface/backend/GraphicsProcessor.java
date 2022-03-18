package com.shootymcshootface.shootymcshootface.backend;

import javafx.scene.image.Image;

import java.io.File;

public class GraphicsProcessor {

    private final String mainDirectory = "." + "/src/main/resources";
    private final String graphicsDirectory = "/Graphics";
    private final String playerDirectory = "/Player";
    private final String backgroundDirectory = "/Background";

    public Image getBackground(){
        return new Image(getClass().getResourceAsStream(mainDirectory + graphicsDirectory + backgroundDirectory + "/DoomFlowerv5C-min.png"));
    }
    public Image playerImage(){
        File file = new File(mainDirectory + graphicsDirectory + playerDirectory + "/playerImage.png");
        System.out.println(file.exists());
        return new Image(getClass().getResourceAsStream(mainDirectory + graphicsDirectory + playerDirectory + "/playerImage.png"));
    }
}
