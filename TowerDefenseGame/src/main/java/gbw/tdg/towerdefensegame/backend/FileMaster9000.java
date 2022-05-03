package gbw.tdg.towerdefensegame.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileMaster9000 {

    //A FileMaster9000 works best in directories which only have max 1 subDirectory.
    //It relies heavily on progressively loading files and their associated names into HashMaps
    //and is thus most effective in applications that requires a lot of the same files to be loaded / accessed
    //during runtime.
    //It is capabable to access files further than 1 directy in compared to it's assigned root

    private final String root;
    private final Map<String,File> knownFileMap;
    private final Map<String,File> knownDirMap;
    private final Map<String,List<File>> knownDirContentsMap;
    private final Map<String,List<File>> knownDirsMap;
    private final String defFileName;
    private File defFile;

    public FileMaster9000(String root, String defaultFile){
        this.root = root;
        this.knownFileMap = new HashMap<>();
        this.knownDirMap = new HashMap<>();
        this.knownDirsMap = new HashMap<>();
        this.knownDirContentsMap = new HashMap<>();
        this.defFileName = defaultFile;
    }

    private List<File> getContents() throws NullPointerException {
        return getContents("");
    }
    private List<File> getContents(String dir) throws NullPointerException {
        return new ArrayList<>(List.of(Objects.requireNonNull(new File(root + dir).listFiles())));
    }
    private List<File> getFiles() throws NullPointerException {
        return getFiles("");
    }

    private List<File> getFiles(String dir) throws NullPointerException {
        List<File> filesKnown = knownDirContentsMap.get(dir);

        if(filesKnown != null){
            return filesKnown;
        }

        List<File> filesFound = getContents(dir).stream().filter(File::isFile).toList();
        knownDirContentsMap.put(dir,filesFound);
        return filesFound;
    }

    private List<File> getDirs() throws NullPointerException {
        return getDirs("");
    }
    private List<File> getDirs(String dir) throws NullPointerException {
        List<File> known = knownDirsMap.get(dir);

        if(known != null){
            return known;
        }

        List<File> filesFound = getContents(dir).stream().filter(File::isDirectory).toList();
        knownDirsMap.put(dir,filesFound);
        return filesFound;
    }

    public File getFile(String request){
        request = request.toLowerCase(Locale.ROOT);

        File known = knownFileMap.get(request);
        if(known != null){
            return known;
        }

        return findFile(request,getFiles());
    }

    public File request(String name) {
        return findFile(name, getFiles());
    }

    private File findFile(String name, List<File> files){
        for(File f : files){
            if(isolateFileName(f).equalsIgnoreCase(name)){
                knownFileMap.put(name,f);
                return f;
            }
        }
        return null;
    }
    private File findDir(String name, List<File> files){
        for(File f : files){
            if(isolateDirectoryName(f).equalsIgnoreCase(name)){
                knownDirMap.put(name,f);
                return f;
            }
        }
        return null;
    }

    public File getFile(String request, File dir){
        return findFile(request, new ArrayList<>(List.of(Objects.requireNonNull(dir.listFiles()))));
    }

    public File requestFrom(String request, String directory){
        request = request.toLowerCase(Locale.ROOT);
        directory = directory.toLowerCase(Locale.ROOT);

        return getFile(request,getDir(directory));
    }

    public File getDir(String name){
        File knownDir = knownDirMap.get(name);

        if(knownDir != null){
            return knownDir;
        }

        for(File f : getDirs()){
            if(isolateDirectoryName(f).equalsIgnoreCase(name)){
                knownDirMap.put(name,f);
                return f;
            }
        }

        return null;
    }

    public List<File> requestAllFilesFrom(String directory) throws NullPointerException{
        return getFiles(directory);
    }

    public File requestOrDefault(String request){
        File found = this.getFile(request);
        return found == null ? getDefault() : found;
    }

    private String isolateFileName(File f){
        String fName = f.toString();
        int indexEnd = fName.lastIndexOf('.');
        int indexStart = fName.lastIndexOf('\\') + 1;
        return fName.substring(indexStart,indexEnd).toLowerCase();
    }

    private String isolateDirectoryName(File f){
        String fName = f.toString();
        int index = fName.lastIndexOf("\\") + 1;
        return fName.substring(index);
    }

    public File getDefault(){
        if(defFile == null){
            defFile = getFile(defFileName);
        }
        return defFile;
    }


}
