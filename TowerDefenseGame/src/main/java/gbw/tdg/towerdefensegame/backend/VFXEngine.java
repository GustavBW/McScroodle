package gbw.tdg.towerdefensegame.backend;

import gbw.tdg.towerdefensegame.Main;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VFXEngine extends IOEngine{

    //Abstracts on IOEngine by being even more specific in what it loads into it's hashmap(s)

    private static VFXEngine instance;
    private final Map<String, List<Image>> dirListImageMap = new HashMap<>();

    public static VFXEngine getInstance(String root){
        if(instance == null){
            instance = new VFXEngine(root);
        }
        return instance;
    }

    protected VFXEngine(String root) {
        super(root, "DefaultUknownFile");
    }

    public Image getRandom(String dir){
        List<Image> fromMap = dirListImageMap.get(dir);

        if (fromMap == null){
            List<File> files = super.getAllOrDefault(dir);

            if(files == null || files.isEmpty()){
                return super.getDefaultImage();
            }

            List<Image> filesToImage = new ArrayList<>();

            for(File f : files){
                filesToImage.add(new Image(f.toURI().toString()));
            }

            dirListImageMap.put(dir,filesToImage);
            fromMap = filesToImage;
        }

        if(fromMap.size() > 1){
            return fromMap.get(Main.random.nextInt(0, fromMap.size()));
        }else{
            return fromMap.get(0);
        }

    }

}
