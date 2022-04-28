package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;

public abstract class IOEngine {

    private File unknownAugmentFile;
    private final FileMaster9000 fm9;

    protected IOEngine(String root, String def){
        this.fm9 = new FileMaster9000(root,def);
    }

    public Image getImage(String request){
        return new Image(fm9.requestOrDefault(request).toURI().toString());
    }

}
