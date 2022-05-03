package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class IceAugment extends Augment {

    //A slow effect reduces enemy to speed to "slowness" percentage of the original

    private LifetimeEffect currentEffect;

    public IceAugment(double value, int type, int level, int maxLevel) {
        super(value,type,level, maxLevel);
    }

    @Override
    public void onSuccesfullApplication(Tower t) {

    }

    @Override
    public synchronized void triggerEffects(Enemy e, Bullet b){
        e.addLifetimeEffect(
                (currentEffect = new SlowEffect(3_000,getSlow(),this))
        );
    }

    private double getSlow(){
        //(log(x) / 2) + 0.1
        return (Math.log10(getLevel()) / 2) + 0.1;
    }

    @Override
    public String getDesc(){
        return "Slows enemies down by " + Decimals.toXDecimalPlaces(getSlow(), 1) * 100 + "% for " + 3 + " seconds";
    }

    @Override
    public String getLongDesc(){
        return getDesc();
    }

    @Override
    public Augment copy() {
        return new IceAugment(this.getValue(), this.getType(), this.getLevel(), this.getMaxLevel());
    }
}
