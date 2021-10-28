package com.Testing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private final GameWindow gameWindow;
    private final Player player;
    private double speed = 5;

    public KeyInput(GameWindow gameWindow, Player player){
        this.gameWindow = gameWindow;
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W){
            player.changeVelY(-1 * speed);
        }
        if (key == KeyEvent.VK_S){
            player.changeVelY(speed);
        }
        if (key == KeyEvent.VK_A){
            player.changeVelX(-1 * speed);
        }
        if (key == KeyEvent.VK_D){
            player.changeVelX(speed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
