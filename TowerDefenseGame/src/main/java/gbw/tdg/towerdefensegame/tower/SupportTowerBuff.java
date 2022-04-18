package gbw.tdg.towerdefensegame.tower;

import gbw.tdg.towerdefensegame.Bullet;

public abstract class SupportTowerBuff {

    protected Tower tower;

    public SupportTowerBuff(Tower t){
        this.tower = t;
    }

    public abstract void applyBuff(Bullet bullet);
}
