package gbw.roguelike.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleasedHandler implements EventHandler<KeyEvent> {


    @Override
    public void handle(KeyEvent keyEvent) {

        switch (keyEvent.getCode()){


            default -> System.out.println("Being needy huh?");
        }
    }
}
