package gbw.roguelike.handlers;

import gbw.roguelike.damagingSystem.DamageInstance;

import java.util.ArrayList;

public class DamageInstanceManager {

    public static ArrayList<DamageInstance> activeInstances = new ArrayList<>();

    public void evaluate(){

        for(DamageInstance d : activeInstances){
            d.evaluateEffects();

            if(d.isFinished()){
                activeInstances.remove(d);
            }
        }
    }

}
