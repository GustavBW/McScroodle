package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;

public class ButtonEngine {

    private static ButtonEngine instance;
    private File unknownInvocationFile;
    private FileMaster9000 fm9;

    public static ButtonEngine getInstance(String root){
        if(instance == null){
            instance = new ButtonEngine(root);
        }
        return instance;
    }

    private ButtonEngine(String root){
        this.fm9 = new FileMaster9000(root);
    }

    public Image getImage(String request){
        return new Image(fm9.requestOrDefault(request,getUnknownButton()).toURI().toString());
    }

    private File getUnknownButton(){
        if(unknownInvocationFile == null){
            unknownInvocationFile = fm9.request("UPButtonBase");
        }
        return unknownInvocationFile;
    }
    public Image getUnknown(){
        return new Image(getUnknownButton().toURI().toString());
    }

}
