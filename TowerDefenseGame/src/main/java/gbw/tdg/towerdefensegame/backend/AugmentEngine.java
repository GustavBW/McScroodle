package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.*;

public class AugmentEngine {

    private static AugmentEngine instance;
    private final String root;
    private Image unknownAugment;
    private final Map<String,Image> strImageMap;

    public static AugmentEngine getInstance(String root){
        if(instance == null){
            instance = new AugmentEngine(root);
        }
        return instance;
    }

    private AugmentEngine(String root){
        this.root = root;
        this.strImageMap = new HashMap<>();
    }

    public Image getIcon(String request){
        String name = request.toLowerCase();
        if(strImageMap.get(name) != null){
            return strImageMap.get(name);
        }

        for(File f : getContents()){
            if(isolateName(f).equalsIgnoreCase(name)){
                strImageMap.put(name, new Image(f.toURI().toString()));
                return strImageMap.get(name);
            }
        }

        return getUnknownAugment();
    }

    private List<File> getContents(){
        return new ArrayList<>(List.of(Objects.requireNonNull(new File(root).listFiles())));
    }

    private Image getUnknownAugment(){
        if(unknownAugment == null){
            for(File f : getContents()){
                if(isolateName(f).equalsIgnoreCase("unknownAugment")){
                    unknownAugment = new Image(f.toURI().toString());
                }
            }
        }
        return unknownAugment;
    }

    private String isolateName(File f){
        String fName = f.toString();
        int indexEnd = fName.lastIndexOf('.');
        int indexStart = fName.lastIndexOf('\\') + 1;
       return fName.substring(indexStart,indexEnd).toLowerCase();
    }

}
