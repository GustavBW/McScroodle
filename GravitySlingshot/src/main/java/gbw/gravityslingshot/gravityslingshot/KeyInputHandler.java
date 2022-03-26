package gbw.gravityslingshot.gravityslingshot;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;
import java.util.List;

public class KeyInputHandler {

    public static List<KeyCode> currentlyActive = new LinkedList<>();

    public void keyPress(KeyEvent e) {
        currentlyActive.add(e.getCode());
    }

    public void keyReleased(KeyEvent e) {
        currentlyActive.remove(e.getCode());
    }
}
