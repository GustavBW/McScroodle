package gbw.tdg.towerdefensegame.tower;

import gbw.tdg.towerdefensegame.Bullet;

public class TowerAtkSpeedBuff extends SupportTowerBuff{

    private double value;

    public TowerAtkSpeedBuff(Tower t, double value) {
        super(t);
        this.value = value;
    }

    @Override
    public void applyBuff(Bullet bullet) {
        bullet.setDamage(bullet.getDamage() + value);
    }
}
