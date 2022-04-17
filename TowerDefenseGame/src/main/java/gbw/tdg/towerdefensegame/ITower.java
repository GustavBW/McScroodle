package gbw.tdg.towerdefensegame;

import java.util.HashSet;
import java.util.Set;

public interface ITower extends Renderable {

    Set<ITower> active = new HashSet<>();
    Set<ITower> expended = new HashSet<>();
    Set<ITower> newborn = new HashSet<>();

    void applyDamageBuff(SupportTowerBuff buff);
    void applyAtkSpeedBuff(SupportTowerBuff buff);

    double getDamage();
}
