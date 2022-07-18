package gbw.circlerenderer;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class RessourceHandler {

    private static String root;
    private static String ressourceDir;
    private String[] acceptedImageTypes = new String[]{
            "png","jpg","PNG","JPG","jfif","JFIF"
    };

    static{
        try {
            root = new File(".").getCanonicalPath();
            ressourceDir = root + "/src/main/resources";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage(){
        File[] getFiles = getFilesInResDir();
        File toUse = null;
        for(File f : getFiles){
            if(isValidImageFile(f.getName())){
                toUse = f;
                break;
            }
        }
        if(toUse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No image found", ButtonType.CLOSE);
            alert.showAndWait();
            return null;
        }
        return new Image(toUse.toURI().toString());
    }

    private File[] getFilesInResDir() {
        return new File(ressourceDir+"/gbw/circlerenderer").listFiles();
    }

    public void saveImageAs(String name, String filetype, Image image){
        //TODO: Resolve Image OUT
        //BufferedImage asBuffered = SwingFXUtils.fromFXImage(image, null);

    }

    public boolean isValidImageFile(String filename){
        File temp = new File(filename);
        if(temp.isDirectory()){return false;}

        String filetype = filename.substring(filename.lastIndexOf(".")+1);
        for(String s : acceptedImageTypes){
            if(s.equalsIgnoreCase(filetype)){
                return true;
            }
        }
        return false;
    }
}
