package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class SpeedUPAugment extends Augment{
    public SpeedUPAugment(double value, int type, int level, int maxLevel) {
        super(value,type,level,maxLevel);
        super.requirement = type;
        super.needsToNotHaveRequirement = true;
    }

    @Override
    public void onSuccesfullApplication(Tower t) {
        t.setAtkSpeed(t.getAtkSpeed() * getIncreasePercent());
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {

    }

    @Override
    public Augment copy() {
        return new SpeedUPAugment(getValue(),getType(),getLevel(),getMaxLevel());
    }

    private double getIncreasePercent(){
        return (1 + (getLevel() / 10D));
    }

    @Override
    public String getDesc(){
        return "Ramps up Tower Attack Speed by " + Decimals.toXDecimalPlaces(getIncreasePercent(),1) + "%.";
    }

    @Override
    public String getLongDesc(){
        return getDesc();
    }
}
