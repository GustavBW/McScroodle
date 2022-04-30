package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.*;

public abstract class IOEngine {

    //Restricts access to the FileMaster.
    //Abstracts a tiny bit by keeping all images requested before

    private final FileMaster9000 fm9;
    private final String def;
    private final Map<String,Image> imageRequestMap = new HashMap<>();

    protected IOEngine(String root, String def){
        this.fm9 = new FileMaster9000(root,def);
        this.def = def;
    }

    public Image getDefaultImage(){
        return getImage(def);
    }

    public Image getImage(String request){
        request = request.toLowerCase(Locale.ROOT);
        Image i = imageRequestMap.get(request);

        if(i != null){
            return i;
        }

        imageRequestMap.put(request, new Image(fm9.requestOrDefault(request).toURI().toString()));
        return imageRequestMap.get(request);
    }

    public List<File> getAll(String dir){
        return fm9.requestAllFilesFrom(dir);
    }

    public List<File> getAllOrDefault(String dir){
        List<File> files = null;

        try{
            files = fm9.requestAllFilesFrom(dir);
        }catch (NullPointerException e){
            return new ArrayList<>(List.of(fm9.getDefault()));
        }

        return files;
    }

    public File getDefault(){
        return fm9.getDefault();
    }

}
