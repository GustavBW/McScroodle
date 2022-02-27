package gbw.roguelike.backend;

import gbw.roguelike.Player;
import gbw.roguelike.enums.AttackAnimationType;
import gbw.roguelike.enums.MovementAnimationTypes;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class GraphicsProcessor {

    private final String graphicsDir = "/graphics";
    private final String projRootToGraphicsDir = ContentEngine.projRootToRessourceDir + graphicsDir;
    private final String playerDir = "/player";
    private final String roomDir = "/rooms";
    private final String MISCDir = "/MISC";
    private final String weaponDir = "/weapons";
    private final String itemDir = "/items";

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

    public HashMap<MovementAnimationTypes, Image[]> getPlayerGraphics() {
        HashMap<MovementAnimationTypes, Image[]> output = new HashMap<>();
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

            MovementAnimationTypes animationFound = determineAnimationType(subFolder);

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

    private MovementAnimationTypes determineAnimationType(File subFolder) {
        MovementAnimationTypes animationFound = MovementAnimationTypes.UNKNOWN;
        String path = subFolder.getAbsolutePath();
        int pos = path.lastIndexOf("\\");
        String animationName =  path.substring(pos + 1);

        for(MovementAnimationTypes a : MovementAnimationTypes.values()){
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

    public HashMap<AttackAnimationType, Image[]> getWeaponAnimations(String name) {
        HashMap<AttackAnimationType, Image[]> output = new HashMap<>();
        String path = projRootToGraphicsDir + weaponDir + "/" + name;

        File dir = new File(path);
        ArrayList<File> foldersFound = new ArrayList<>();

        for(File f : Objects.requireNonNull(dir.listFiles())){
            if(f.isDirectory()){
                foldersFound.add(f);
            }
        }

        ArrayList<Image> imagesFound = new ArrayList<>();

        for(File subFolder : foldersFound){

            AttackAnimationType currectAnimationType = getWeaponAnimationType(subFolder);
            System.out.println("Found weapon animation " + currectAnimationType);

            for(File file : Objects.requireNonNull(subFolder.listFiles())){

                if(fileIsOfType(file,"png")){
                    imagesFound.add(new Image(file.toURI().toString()));
                }

            }

            output.put(currectAnimationType, imagesFound.toArray(new Image[0]));
            imagesFound.clear();
        }

        return output;
    }

    private AttackAnimationType getWeaponAnimationType(File subFolder) {
        AttackAnimationType animationFound = AttackAnimationType.UNKNOWN;
        String path = subFolder.getAbsolutePath();
        int pos = path.lastIndexOf("\\");
        String folderName =  path.substring(pos + 1);

        for(AttackAnimationType a : AttackAnimationType.values()){
            if(a.name.equalsIgnoreCase(folderName)){
                animationFound = a;
                break;
            }
        }
        return animationFound;
    }

    public Image getItemGraphics(int id) {
        Image output = null;
        String path = projRootToGraphicsDir + itemDir + "/" + id + ".png";
        File image = new File(path);

        if(!image.exists()){
            output = getGraphicsNotFound();
        }else{
            output = new Image(image.toURI().toString());
        }

        return output;
    }
}
