package gbw.roguelike;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class GraphicsProcessor {

    private final String graphicsDir = "/graphics";
    private final String roomDir = "/rooms";
    private final String MISCDir = "/MISC";

    public Image[] getRoomGraphics(int id){
        String dirPath = graphicsDir + roomDir + "/" + id;

        System.out.println(dirPath);
        ArrayList<Image> imagesFound = new ArrayList<>();

        File dir = new File(dirPath);
        try {
            System.out.println("Canonical path " + dir.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] filesInDir = dir.listFiles();
        System.out.println("FilesInDir is : " + filesInDir);

        if(filesInDir.length < 0) {

            for (File f : filesInDir) {
                imagesFound.add(new Image(getClass().getResourceAsStream(f.getAbsolutePath())));
            }

        }else{
            return new Image[]{getGraphicsNotFound()};
        }

        Image[] output = new Image[imagesFound.size()];
        for(int i = 0; i < imagesFound.size(); i++){
            output[i] = imagesFound.get(i);
        }

        return output;
    }

    public Image getGraphicsNotFound(){
        Image output;
        output = new Image(getClass().getResourceAsStream(graphicsDir + MISCDir + "/QuestionMark.png"));
        return output;
    }

}
