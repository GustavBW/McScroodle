package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;

public class ButtonEngine extends IOEngine{

    private static ButtonEngine instance;

    public static ButtonEngine getInstance(String root){
        if(instance == null){
            instance = new ButtonEngine(root);
        }
        return instance;
    }

    private ButtonEngine(String root){
        super(root,"UPButtonBase");
    }
}
