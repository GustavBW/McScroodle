package gbw.roguelike;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;


public class GraphicsProcessor {

    private final String graphicsDir = "/graphics";
    private final String projRootToGraphicsDir = "src/main/resources" + graphicsDir;
    private final String roomDir = "/rooms";
    private final String MISCDir = "/MISC";

    public Image[] getRoomGraphics(int id){
        String dirPath = projRootToGraphicsDir + roomDir + "/" + id;

        ArrayList<Image> imagesFound = new ArrayList<>();
        File dir = new File(dirPath);
        File[] filesInDir = dir.listFiles();

        assert filesInDir != null;
        if(filesInDir.length > 0) {

            for (File f : filesInDir) {
                imagesFound.add(new Image(f.toURI().toString()));
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
        output = new Image(getClass().getResourceAsStream("/graphics" + MISCDir + "/QuestionMark.png"));
        return output;
    }

}
