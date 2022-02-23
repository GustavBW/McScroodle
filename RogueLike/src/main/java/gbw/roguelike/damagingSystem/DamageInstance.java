package gbw.roguelike.damagingSystem;

import java.util.ArrayList;

public class DamageInstance {

    private Object owner;   //Who created this Instance
    private Damagable host; //Who this Instance is applied to
    private ArrayList<DamageInstanceEffect> effects;
    private long spawnTimestamp;

    public DamageInstance(Object owner, ArrayList<DamageInstanceEffect> effects, Damagable host){
        this.host = host;
        this.owner = owner;
        this.effects = effects;
        this.spawnTimestamp = System.nanoTime();
    }

    public void evaluateEffects(){

        for(DamageInstanceEffect DIE : effects){
            switch (DIE.effectType){
                case DAMAGE -> host.applyDamage(DIE.value, DIE.damageType);

                case SLOW -> {

                }
                case BURNING -> host.applyDamage(DIE.value, DIE.damageType);

            }

            if(System.nanoTime() > spawnTimestamp + (DIE.duration * 1_000_000_000)){
                effects.remove(DIE);
            }
        }
    }

    public boolean isFinished(){
        return effects.size() == 0;
    }

}
