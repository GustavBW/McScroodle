package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class DamageUpAugment extends Augment{

    public DamageUpAugment(double value, int type, int level, int maxLevel) {
        super(value,type,level,maxLevel);
        super.requirement = type;
        super.needsToNotHaveRequirement = true;
    }

    @Override
    public void onSuccesfullApplication(Tower t) {
        t.setDamage(t.getDamage() + getLevel());
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {

    }

    @Override
    public String getDesc(){
        return "Increased Tower base damage by " + getLevel() + ".";
    }

    @Override
    public String getLongDesc(){
        return getDesc();
    }



    @Override
    public Augment copy() {
        return new DamageUpAugment(getValue(),getType(),getLevel(),getMaxLevel());
    }
}
