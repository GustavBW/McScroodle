package gbw.roguelike.damagingSystem;

import gbw.roguelike.enums.DamageEffectType;
import gbw.roguelike.enums.DamageType;

public class DamageInstanceEffect {

    public double value, duration;
    public DamageEffectType effectType;
    public DamageType damageType;
    public long activationTimestamp;

    public DamageInstanceEffect(double value, double duration, DamageEffectType type){
        this.value = value;
        this.duration = duration;
        this.effectType = type;
    }
}


