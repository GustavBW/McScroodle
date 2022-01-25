package gbw.winnions.winnions;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleaseHandler implements EventHandler<KeyEvent> {

    private Player localPlayer;
    private PlayerCamera localPlayerCamera;

    public KeyReleaseHandler(Player p, PlayerCamera pc){

        this.localPlayer = p;
        this.localPlayerCamera = pc;

    }

    @Override
    public void handle(KeyEvent keyEvent) {


        switch (keyEvent.getCode()){




            default -> {
                localPlayer.removeInput(keyEvent);
                System.out.println("Released: " + keyEvent.getCode());
            }


        }

    }
}
