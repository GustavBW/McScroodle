package com.Testing;

import com.Testing.Interfaces.Interactable;

public class InteractionHandler implements Runnable{

    public InteractionHandler(){
        System.out.println("INTER INTER HYPE");
    }

    double interpolation = 0;
    private final int TICKS_PER_SECOND = 60, SKIP_TICKS = 1000 / TICKS_PER_SECOND, MAX_FRAMESKIP = 5;

    @Override
    public synchronized void run() {

        int loops = 0;
        double next_game_tick = System.currentTimeMillis();

        while(Game.isRunning){
            loops = 0;
            while(System.currentTimeMillis() < next_game_tick && loops < MAX_FRAMESKIP){
                next_game_tick += SKIP_TICKS;
                loops++;
            }
            Tick();
        }
    }

    public void Tick(){
        for(Interactable t : Interactable.interactables){

        }
    }
}
