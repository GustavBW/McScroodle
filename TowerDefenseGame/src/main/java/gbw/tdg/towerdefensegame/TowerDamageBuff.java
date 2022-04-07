package gbw.tdg.towerdefensegame;

public class TowerDamageBuff extends SupportTowerBuff{

    private double value;

    public TowerDamageBuff(Tower t, double value) {
        super(t);
        this.value = value;
    }

    @Override
    public void applyBuff(Bullet bullet) {
        bullet.damage += value;
    }

}
