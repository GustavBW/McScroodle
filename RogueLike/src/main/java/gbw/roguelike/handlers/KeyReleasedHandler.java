package gbw.roguelike.handlers;

import gbw.roguelike.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyReleasedHandler implements EventHandler<KeyEvent> {

    private final Player player;

    public KeyReleasedHandler(Player localPlayer) {
        this.player = localPlayer;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

        KeyCode key = keyEvent.getCode();

        switch (key){


            default -> player.removeInput(key);
        }
    }
}
