package gbw.roguelike;

import javafx.scene.image.Image;

public class ContentEngine {

    private static GraphicsProcessor gp = new GraphicsProcessor();
    private static TextProcessor tp = new TextProcessor();

    public static Image[] getRoomGraphics(int id){
        return gp.getRoomGraphics(id);
    }

    public static Image getGraphicsNotFound(){
        return gp.getGraphicsNotFound();
    }
}
