package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;

public class AugmentEngine {

    private static AugmentEngine instance;
    private File unknownAugmentFile;
    private final FileMaster9000 fm9;

    public static AugmentEngine getInstance(String root){
        if(instance == null){
            instance = new AugmentEngine(root);
        }
        return instance;
    }

    private AugmentEngine(String root){
        this.fm9 = new FileMaster9000(root);
    }

    public Image getImage(String request){
        return new Image(fm9.requestOrDefault(request,getUnknownAugment()).toURI().toString());
    }

    private File getUnknownAugment(){
        if(unknownAugmentFile == null){
            unknownAugmentFile = fm9.request("unknownAugment");
        }
        return unknownAugmentFile;
    }
}
