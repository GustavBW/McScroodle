package gbw.roguelike.handlers;

import gbw.roguelike.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements EventHandler<KeyEvent> {


    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){

            //RESERVED KEY'S FOR GENEREL GAME HOTKEYS LIKE MENUS
            case P -> {
                Main.onPause = !Main.onPause;
            }

            //ALL KEY INPUTS THAT DOESN'T MATCH THE ABOVE, ARE SENT TO THE PLAYER
            default -> {
                System.out.println("Default triggered");
            }
        }
    }
}
