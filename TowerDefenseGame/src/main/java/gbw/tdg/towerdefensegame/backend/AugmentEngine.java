package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;

public class AugmentEngine extends IOEngine {

    private static AugmentEngine instance;

    public static AugmentEngine getInstance(String root){
        if(instance == null){
            instance = new AugmentEngine(root);
        }
        return instance;
    }

    private AugmentEngine(String root){
        super(root, "unknownAugment");
    }
}
