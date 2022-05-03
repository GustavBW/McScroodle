package gbw.tdg.towerdefensegame.backend;

import javafx.scene.image.Image;

import java.io.*;
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

    public List<String> readFile(File f){
        List<String> toReturn = new ArrayList<>();

        try{
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(f));

            while((line = br.readLine()) != null){
                toReturn.add(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toReturn;
    }
    public void writeToFile(File f, List<String> list){
        String s = TextFormatter.concatonateArray(list, "\n");
        writeToFile(f,s);
    }
    public void writeToFile(File f, String s){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(s);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File request(String name){
        return fm9.request(name);
    }

    public File getDefault(){
        return fm9.getDefault();
    }

}
