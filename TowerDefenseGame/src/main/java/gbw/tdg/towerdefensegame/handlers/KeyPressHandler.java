package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.DevTools;
import gbw.tdg.towerdefensegame.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class KeyPressHandler implements EventHandler<KeyEvent> {

    private final UIController UIC;
    private static final DevTools devTools = new DevTools();
    public static Set<KeyCode> currentInputs = new HashSet<>();

    public KeyPressHandler(UIController UIC){
        this.UIC = UIC;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){

            case P -> Main.onPause = !Main.onPause;
            case F3 -> UIC.toggleInfo();


            default -> System.out.println("You pressed: " + keyEvent.getCode());
        }
    }
    public void press(KeyEvent keyEvent){
        currentInputs.add(keyEvent.getCode());

        switch (keyEvent.getCode()){

            case P -> Main.onPause = !Main.onPause;
            case F3 -> UIC.toggleInfo();


            //default -> System.out.println("You pressed: " + keyEvent.getCode());
        }

        if(currentInputs.contains(KeyCode.G) && currentInputs.contains(KeyCode.B) && currentInputs.contains(KeyCode.W)){

            devTools.toggleExistence();
        }

    }
    public void release(KeyEvent keyEvent){
        currentInputs.remove(keyEvent.getCode());
    }
}
