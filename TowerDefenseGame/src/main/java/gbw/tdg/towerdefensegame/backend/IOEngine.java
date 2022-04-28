package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class IOEngine {

    private final FileMaster9000 fm9;
    private final Map<String,Image> imageRequestMap = new HashMap<>();

    protected IOEngine(String root, String def){
        this.fm9 = new FileMaster9000(root,def);
    }

    public Image getImage(String request){
        request = request.toLowerCase(Locale.ROOT);
        System.out.println("IOEngine: Request " + request + "===============");
        Image i = imageRequestMap.get(request);

        if(i != null){
            System.out.println("Request found in image map ");
            return i;
        }

        imageRequestMap.put(request, new Image(fm9.requestOrDefault(request).toURI().toString()));
        return imageRequestMap.get(request);
    }

}
