package gbw.tdg.towerdefensegame.backend;

import java.io.File;
import java.util.*;

public class FileMaster9000 {

    //A FileMaster9000 expects there to be NO directories in the root it is given
    //It supports adding additional files to it's root during runtime, and is thus optimized
    //to account for the performance drawback by using a HashMap to look up previously found files

    private final String root;
    private final Map<String,File> strFileMap;
    private final String defFileName;
    private File defFile;

    public FileMaster9000(String root, String defaultFile){
        this.root = root;
        this.strFileMap = new HashMap<>();
        this.defFileName = defaultFile;
    }

    private List<File> getContents(){
        List<File> list = new ArrayList<>(List.of(Objects.requireNonNull(new File(root).listFiles())));
        return list.stream().filter(File::isFile).toList();
    }

    public File request(String request){
        System.out.println("FM9 request " + request);
        request = request.toLowerCase(Locale.ROOT);

        if(strFileMap.get(request) != null){
            System.out.println("FM9: Found file in Map ");
            return strFileMap.get(request);
        }

        for(File f : getContents()){
            if(isolateName(f).equalsIgnoreCase(request)){
                strFileMap.put(request,f);
                System.out.println("FM9: File found in contents");
                return strFileMap.get(request);
            }
        }
        return getDefault();
    }

    public File requestOrDefault(String request){
        File found = this.request(request);
        return found == null ? getDefault() : found;
    }

    private String isolateName(File f){
        String fName = f.toString();
        int indexEnd = fName.lastIndexOf('.');
        int indexStart = fName.lastIndexOf('\\') + 1;
        return fName.substring(indexStart,indexEnd).toLowerCase();
    }

    private File getDefault(){
        if(defFile == null){
            defFile = request(defFileName);
        }
        return defFile;
    }
}
