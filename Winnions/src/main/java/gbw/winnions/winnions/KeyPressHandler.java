package gbw.winnions.winnions;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements EventHandler<KeyEvent> {

    private Player localPlayer;
    private PlayerCamera localPlayerCamera;

    public KeyPressHandler(Player p, PlayerCamera pc){

        this.localPlayer = p;
        this.localPlayerCamera = pc;

    }



    @Override
    public void handle(KeyEvent keyEvent) {

        localPlayer.addInput(keyEvent);

        switch (keyEvent.getCode()) {

            case Y -> localPlayerCamera.toggleLock();

            case ESCAPE -> {}
        }




    }



}
