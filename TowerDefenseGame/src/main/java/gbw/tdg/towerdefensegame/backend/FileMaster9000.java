package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.*;

public class FileMaster9000 {

    //A FileMaster9000 expects there to be NO directories in the root it is given
    //It supports adding additional files to it's root during runtime, and is thus optimized
    //to account for the performance drawback by using a HashMap to look up previously found files

    private final String root;
    private final Map<String,File> strImageMap;

    public FileMaster9000(String root){
        this.root = root;
        this.strImageMap = new HashMap<>();
    }

    private List<File> getContents(){
        List<File> list = new ArrayList<>(List.of(Objects.requireNonNull(new File(root).listFiles())));
        return list.stream().filter(File::isFile).toList();
    }

    public File request(String request){
        request = request.toLowerCase(Locale.ROOT);

        if(strImageMap.get(request) != null){
            return strImageMap.get(request);
        }

        for(File f : getContents()){
            if(isolateName(f).equalsIgnoreCase(request)){
                strImageMap.put(request,f);
                return strImageMap.get(request);
            }
        }
        return null;
    }

    public File requestOrDefault(String request, File def){
        File found = this.request(request);
        return found == null ? def : found;
    }

    private String isolateName(File f){
        String fName = f.toString();
        int indexEnd = fName.lastIndexOf('.');
        int indexStart = fName.lastIndexOf('\\') + 1;
        return fName.substring(indexStart,indexEnd).toLowerCase();
    }
}
