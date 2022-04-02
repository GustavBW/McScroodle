package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements EventHandler<KeyEvent> {

    private final UIController UIC;

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
}
