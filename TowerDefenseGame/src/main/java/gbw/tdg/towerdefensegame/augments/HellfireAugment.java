package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class HellfireAugment extends Augment{

    private double totalBurn;
    private final int durationMS = 3_000;

    protected HellfireAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level, maxLevel);
    }

    @Override
    public void onSuccesfullApplication(Tower t) {

    }

    @Override
    public void triggerEffects(Enemy e, Bullet b){
        totalBurn = b.getDamage() * (0.2 * getLevel()) + getLevel();

        e.addLifetimeEffect(
                new BurnEffect(getTotalBurnPercent(),durationMS,this)
        );
    }

    private double getTotalBurnPercent(){
        return 100 * 0.2 * getLevel();
    }

    @Override
    public String getDesc(){
        return "Blazing bullets dealing " + Decimals.toXDecimalPlaces(getTotalBurnPercent(),0)
                + "% of bullet damage + " + getLevel() + " over " + (durationMS / 1_000) + " seconds";
    }

    @Override
    public String getLongDesc(){
        return getDesc();
    }

    @Override
    public Augment copy() {
        return new HellfireAugment(this.getValue(), this.getType(), this.getLevel(), this.getMaxLevel());
    }
}
