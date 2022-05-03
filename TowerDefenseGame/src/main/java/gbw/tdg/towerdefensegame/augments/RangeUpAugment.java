package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class RangeUpAugment extends Augment{

    public RangeUpAugment(double value, int type, int level, int maxLevel) {
        super(value,type,level,maxLevel);
        super.requirement = type;
        super.needsToNotHaveRequirement = true;
    }

    @Override
    public void onSuccesfullApplication(Tower t) {
        t.setRange(t.getRangeBase() * getIncreasePercent());
    }

    private double getIncreasePercent(){
        return 1 + (getLevel() / 10D);
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {

    }

    @Override
    public Augment copy() {
        return new RangeUpAugment(getValue(),getType(),getLevel(),getMaxLevel());
    }

    @Override
    public String getDesc(){
        return "Increases Tower range by " + Decimals.toXDecimalPlaces(getIncreasePercent(),1) * 100 + "%.";
    }

    @Override
    public String getLongDesc(){
        return getDesc();
    }
}
