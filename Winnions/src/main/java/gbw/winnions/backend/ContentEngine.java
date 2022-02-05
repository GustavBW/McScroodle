package gbw.winnions.backend;

import javafx.scene.image.Image;

public abstract class ContentEngine {



    public static Image getDefaultGraphics(){
        return new GraphicsProcessor().getDefaultGraphics();
    }
}
