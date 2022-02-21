package com.shootymcshootface.shootymcshootface.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GraphicsProcessor {

    private final String mainDirectory = "resources";
    private final String graphicsDirectory = "/Graphics";
    private final String playerDirectory = "/Player";
    private final String backgroundDirectory = "/Background";

    public Image getBackground(){
        return new Image(getClass().getResourceAsStream(mainDirectory + graphicsDirectory + backgroundDirectory + "/DoomFlowerv5C-min.png"));
    }
    public Image playerImage(){
        File file = new File(mainDirectory + graphicsDirectory + playerDirectory);
        System.out.println(file.getAbsolutePath());
        for(File f : file.listFiles()){
            System.out.println(f.getAbsolutePath());
        }
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(mainDirectory + graphicsDirectory + playerDirectory + "/New_Piskel-1.png.png")));
    }
}
