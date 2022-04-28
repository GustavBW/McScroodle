package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class InvocationEngine extends IOEngine {

    private static InvocationEngine instance;

    public static InvocationEngine getInstance(String root){
        if(instance == null){
            instance = new InvocationEngine(root);
        }
        return instance;
    }

    private InvocationEngine(String root){
        super(root, "unknownInvocation");
    }

}
