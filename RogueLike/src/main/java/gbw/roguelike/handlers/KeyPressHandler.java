package gbw.roguelike.handlers;

import gbw.roguelike.Main;
import gbw.roguelike.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements EventHandler<KeyEvent> {

    private final Player player;

    public KeyPressHandler(Player p){
        this.player = p;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();
        switch (key){

            //RESERVED KEY'S FOR GENEREL GAME HOTKEYS LIKE MENUS
            case P -> {
                Main.onPause = !Main.onPause;
            }
            case E ->{
                InteractionHandler.evaluateInteractibles();
            }

            //ALL KEY INPUTS THAT DOESN'T MATCH THE ABOVE, ARE SENT TO THE PLAYER
            default -> player.addInput(key);
        }
    }
}
