package com.shootymcshootface.shootymcshootface.gameloop;

import com.shootymcshootface.shootymcshootface.gameobject.Player;

public class CollisionHandler implements Runnable{

    public static boolean nextMoveIsValid = false, isWaiting, isRunning;

    private Player player;
    private boolean awaitBoolean = true;

    public CollisionHandler(Player player){
        this.player = player;

    }
    @Override
    public void run() {
        isRunning = false;
        while(Game.isRunning || RenderHandler.isRunning){
            syncItUp();



        }
    }

    private void syncItUp(){
        while(!RenderHandler.isReady){
            if(awaitBoolean){
                System.out.println("CollisionHandler is awaiting at " + System.nanoTime());
                awaitBoolean = !awaitBoolean;
            }
            System.out.println("");
        }
    }
}
