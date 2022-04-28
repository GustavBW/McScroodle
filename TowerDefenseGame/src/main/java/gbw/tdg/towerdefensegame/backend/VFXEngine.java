package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

public class VFXEngine extends IOEngine{

    private static VFXEngine instance;

    public static VFXEngine getInstance(String root){
        if(instance == null){
            instance = new VFXEngine(root);
        }
        return instance;
    }


    protected VFXEngine(String root) {
        super(root, "LightningVFX");
    }

}
