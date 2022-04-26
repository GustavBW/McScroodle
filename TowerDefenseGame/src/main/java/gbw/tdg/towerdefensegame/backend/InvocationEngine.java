package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class InvocationEngine {

    private static InvocationEngine instance;
    private File unknownInvocationFile;
    private FileMaster9000 fm9;

    public static InvocationEngine getInstance(String root){
        if(instance == null){
            instance = new InvocationEngine(root);
        }
        return instance;
    }

    private InvocationEngine(String root){
        this.fm9 = new FileMaster9000(root);
    }

    public Image getImage(String request){
        return new Image(fm9.requestOrDefault(request,getUnknownInvocation()).toURI().toString());
    }

    private File getUnknownInvocation(){
        if(unknownInvocationFile == null){
            unknownInvocationFile = fm9.request("unknownInvocation");
        }
        return unknownInvocationFile;
    }
    public Image getUnknown(){
        return new Image(getUnknownInvocation().toURI().toString());
    }


}
