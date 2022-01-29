package gbw.winnions.backend;

import javafx.scene.image.Image;

import java.util.Objects;

public class GraphicsProcessor {

    private static String miscDirectory = "/gbw/winnions";

    public Image getDefaultGraphics(){
        return new Image(getClass().getResourceAsStream(miscDirectory + "/QuestionMark.png"));
    }
}
