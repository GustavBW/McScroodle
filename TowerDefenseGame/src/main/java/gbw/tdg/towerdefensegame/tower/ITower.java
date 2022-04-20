package gbw.tdg.towerdefensegame.tower;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.Clickable;

import java.util.HashSet;
import java.util.Set;

public abstract class ITower implements Clickable, Renderable, Tickable {

    public static Set<ITower> active = new HashSet<>();
    public static Set<ITower> expended = new HashSet<>();
    public static Set<ITower> newborn = new HashSet<>();

    public abstract void applyDamageBuff(SupportTowerBuff buff);
    public abstract void applyAtkSpeedBuff(SupportTowerBuff buff);

    public abstract double getDamage();
}
