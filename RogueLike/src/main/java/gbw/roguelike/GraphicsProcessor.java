package gbw.roguelike;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class GraphicsProcessor {

    private final String graphicsDir = "src/main/resources/graphics";
    private final String roomDir = "/rooms";
    private final String MISCDir = "/graphics/MISC";

    public Image[] getRoomGraphics(int id){
        String dirPath = graphicsDir + roomDir + "/" + id;
        File dir = new File(dirPath);
        File[] filesInDir = dir.listFiles();

        Image[] output = new Image[filesInDir.length];


        if(filesInDir.length < 0) {

            for (int i = 0; i < filesInDir.length; i++) {
                output[i] = new Image(getClass().getResourceAsStream(filesInDir[i].getAbsolutePath()));
            }

        }else{
            return new Image[]{getGraphicsNotFound()};
        }

        return output;
    }


    public Image getGraphicsNotFound(){
        Image output;
        output = new Image(Objects.requireNonNull(getClass().getResourceAsStream(graphicsDir + MISCDir + "/QuestionMark.png")));
        return output;
    }

}
