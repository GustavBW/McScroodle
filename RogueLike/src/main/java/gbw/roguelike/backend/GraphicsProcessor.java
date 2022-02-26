package gbw.roguelike.backend;

import gbw.roguelike.Player;
import gbw.roguelike.enums.AnimationType;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class GraphicsProcessor {

    private final String graphicsDir = "/graphics";
    private final String projRootToGraphicsDir = "src/main/resources" + graphicsDir;
    private final String playerDir = "/player";
    private final String roomDir = "/rooms";
    private final String MISCDir = "/MISC";

    public Image[] getRoomGraphics(int id){
        String dirPath = projRootToGraphicsDir + roomDir + "/" + id;

        ArrayList<Image> imagesFound = new ArrayList<>();
        File dir = new File(dirPath);
        File[] filesInDir = dir.listFiles();

        assert filesInDir != null;
        if(filesInDir.length > 0) {

            for (File f : filesInDir) {
                imagesFound.add(new Image(f.toURI().toString()));
            }

        }else{
            return new Image[]{getGraphicsNotFound()};
        }

        Image[] output = new Image[imagesFound.size()];
        for(int i = 0; i < imagesFound.size(); i++){
            output[i] = imagesFound.get(i);
        }

        return output;
    }

    public Image getGraphicsNotFound(){
        Image output;
        output = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics" + MISCDir + "/QuestionMark.png")));
        return output;
    }

    public HashMap<AnimationType, Image[]> getPlayerGraphics() {
        HashMap<AnimationType, Image[]> output = new HashMap<>();
        String playerDirFromRoot = projRootToGraphicsDir + playerDir;
        File dir = new File(playerDirFromRoot);
        ArrayList<File> foldersFound = new ArrayList<>();

        for(File f : Objects.requireNonNull(dir.listFiles())){
            if(f.isDirectory()){
                foldersFound.add(f);
            }
        }

        ArrayList<Image> imagesFound = new ArrayList<>();

        for(File subFolder : foldersFound){

            AnimationType animationFound = determineAnimationType(subFolder);

            for(File f : Objects.requireNonNull(subFolder.listFiles())){

                if(fileIsOfType(f, "png")){
                    imagesFound.add(new Image(f.toURI().toString()));

                }else if(fileIsOfType(f, "txt")){

                    Player.animationLengthSeconds.put(animationFound,readAnimationLengthFromConfig(f));
                }

            }

            Image[] animation = imagesFound.toArray(new Image[0]);
            output.put(animationFound, animation);

            imagesFound.clear();
        }

        return output;
    }

    private boolean fileIsOfType(File f, String t){
        String fPath = f.getAbsolutePath();
        String fType = fPath.substring(fPath.length() - t.length());
        return fType.equalsIgnoreCase(t);
    }
    private AnimationType determineAnimationType(File subFolder) {
        AnimationType animationFound = AnimationType.UNKNOWN;
        String path = subFolder.getAbsolutePath();
        int pos = path.lastIndexOf("\\");
        String animationName =  path.substring(pos + 1);

        System.out.println("Found player animation " + animationName);
        for(AnimationType a : AnimationType.values()){
            if(a.name.equalsIgnoreCase(animationName)){
                animationFound = a;
                break;
            }
        }
        return animationFound;
    }
    private double readAnimationLengthFromConfig(File f) {

        double value = 1.00D;

        try{

            BufferedReader br = new BufferedReader(new FileReader(f));

            value = Double.parseDouble(br.readLine());

            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return value;
    }
}
