package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.LifetimeEffect;

public class IceAugment extends Augment {

    //A slow effect reduces enemy to speed to "slowness" percentage of the original

    private double slowness;
    private LifetimeEffect currentEffect;

    public IceAugment(double value, int type, int level, int maxLevel) {
        super(value,type,level, maxLevel);
        this.slowness = Decimals.toXDecimalPlaces(1 - (0.2 * level), 2);
    }

    @Override
    public synchronized void triggerEffects(Enemy e, Bullet b){
        slowness = Decimals.toXDecimalPlaces(1 - (0.2 * getLevel()), 2);

        e.addLifetimeEffect(
                (currentEffect = new SlowEffect(3_000,slowness,this))
        );
    }

    @Override
    public String getDesc(){
        int lifetime = currentEffect == null ? getLevel() : (int) (currentEffect.getLifetimeMS() / 1000);
        slowness = Decimals.toXDecimalPlaces(1 - (0.2 * getLevel()), 2);
        return "Slows enemies down by " + (100 - slowness * 100) + "% for " + lifetime + " seconds";
    }

    @Override
    public Augment copy() {
        return new IceAugment(this.getValue(), this.getType(), this.getLevel(), this.getMaxLevel());
    }
}
