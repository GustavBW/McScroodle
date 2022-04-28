package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.LifetimeEffect;

public class IceAugment extends Augment {

    //A slow effect reduces enemy to speed to "slowness" percentage of the original

    private LifetimeEffect currentEffect;

    public IceAugment(double value, int type, int level, int maxLevel) {
        super(value,type,level, maxLevel);
    }

    @Override
    public synchronized void triggerEffects(Enemy e, Bullet b){
        e.addLifetimeEffect(
                (currentEffect = new SlowEffect(3_000,getSlow(),this))
        );
    }

    private double getSlow(){
        //A "slow" percentage is what percentage of original movespeed, the enemies current movespeed is reduced to.
        return 1 / (getLevel() * .5);
    }

    @Override
    public String getDesc(){
        int lifetime = currentEffect == null ? getLevel() : (int) (currentEffect.getLifetimeMS() / 1000);
        double d = Decimals.toXDecimalPlaces(getSlow(), 2);
        return "Slows enemies down by " + (100 - d * 100) + "% for " + lifetime + " seconds";
    }

    @Override
    public Augment copy() {
        return new IceAugment(this.getValue(), this.getType(), this.getLevel(), this.getMaxLevel());
    }
}
