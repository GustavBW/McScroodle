package gbw.tdg.towerdefensegame.backend;

import java.io.File;
import java.io.IOException;

public class ContentEngine {

    private static String root;

    static{
        File file = new File(".");
        try {
            root = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root = root + "/src/main/resources/gbw/tdg/towerdefensegame";
    }
    public static AugmentEngine AUGMENTS = AugmentEngine.getInstance(root + "/augments");

}
