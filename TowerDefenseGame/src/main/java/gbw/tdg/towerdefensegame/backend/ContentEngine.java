package gbw.tdg.towerdefensegame.backend;

import java.io.File;
import java.io.IOException;

public class ContentEngine {

    private static String root;
    private static String ressourceDir;

    static{
        File file = new File(".");
        try {
            root = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ressourceDir = root + "/src/main/resources/gbw/tdg/towerdefensegame";
    }

    public static AugmentEngine AUGMENTS = AugmentEngine.getInstance(ressourceDir + "/augments");
    public static InvocationEngine INVOCATIONS = InvocationEngine.getInstance(ressourceDir + "/invocations");
    public static ButtonEngine BUTTONS = ButtonEngine.getInstance(ressourceDir + "/buttons");
}
