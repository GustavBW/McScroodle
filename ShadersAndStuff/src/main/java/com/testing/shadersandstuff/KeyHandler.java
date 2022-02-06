package com.testing.shadersandstuff;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {


    @Override
    public void handle(KeyEvent keyEvent) {


        switch (keyEvent.getCode()){

            case P -> Main.pause = !Main.pause;


        }
    }
}
