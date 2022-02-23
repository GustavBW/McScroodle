package gbw.roguelike.damagingSystem;

import gbw.roguelike.enums.BaseStatsType;
import gbw.roguelike.GameObject;
import gbw.roguelike.enums.DamageType;

import java.util.HashMap;

public abstract class Damagable extends GameObject {

    public abstract double applyDamage(double amount, DamageType type);
    public abstract HashMap<BaseStatsType, Double> getBaseStats();

}
