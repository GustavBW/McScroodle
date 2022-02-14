package gbw.tdg.towerdefensegame;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements EventHandler<KeyEvent> {


    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){

            case P : Main.onPause = !Main.onPause;



            default : System.out.println("You pressed: " + keyEvent.getCode());
        }
    }
}
