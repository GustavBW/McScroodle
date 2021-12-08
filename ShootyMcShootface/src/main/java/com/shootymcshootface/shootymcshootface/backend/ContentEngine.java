package com.shootymcshootface.shootymcshootface.backend;

import javafx.scene.image.Image;

public class ContentEngine {



    public static Image getBackground(){return new GraphicsProcessor().getBackground();}
    public static Image getPlayerImage(){
        return new GraphicsProcessor().playerImage();
    }


}
