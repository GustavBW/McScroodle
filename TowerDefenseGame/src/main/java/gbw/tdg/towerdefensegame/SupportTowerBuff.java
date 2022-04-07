package gbw.tdg.towerdefensegame;

public abstract class SupportTowerBuff {

    protected Tower tower;

    public SupportTowerBuff(Tower t){
        this.tower = t;
    }

    public abstract void applyBuff(Bullet bullet);
}
