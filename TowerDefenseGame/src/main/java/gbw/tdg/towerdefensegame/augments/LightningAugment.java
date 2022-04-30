package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.UI.vfx.TopDownStrikeVFX;
import gbw.tdg.towerdefensegame.UI.vfx.VFX;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class LightningAugment extends Augment{

    private double maxDmgAt = 0.3;

    protected LightningAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level,maxLevel);
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {
        int delayMS = getDelayMS();
        new BounceBackImpulse<>(delayMS, this, enemyHit, bullet).spawn();
        enemyHit.addLifetimeEffect(new LifetimeEffect(this,delayMS){
            @Override
            public String getEffectString(){
                return "IMPENDING DOOM " + timeRemaining / 1000;
            }
        });
    }

    @Override
    public void bounce(Enemy e, Bullet b){
        e.applyDamage(e.getMaxHP() * getPercentMaxHPDamage(e) + b.getDamage());
        new TopDownStrikeVFX(200, VFX.DEFAULT_PRIO, e.getPosition(), ContentEngine.VFX.getRandom("/lightnings")).spawn();
    }

    @Override
    public Augment copy() {
        return new LightningAugment(this.getValue(), this.getType(), this.getLevel(),this.getMaxLevel());
    }
    private double getPercentMaxHPDamage(){
        return (2 * Math.log10(getLevel()) + 1) / 10.0;
    }
    private double getPercentMaxHPDamage(Enemy e){
        return getPercentMaxHPDamage() * (1 - e.getPercentMissingHealth(- maxDmgAt));
    }

    private int getDelayMS(){
        int toreturn = (int) ((3 * (1.0 / getLevel())) * 1000);

        return toreturn;
    }

    @Override
    public String getDesc(){
        return "A lightning strikes for up to " + (int) (getPercentMaxHPDamage() * 100)
                + " % max hp damage after a " + getDelayMS() / 1000 + " s delay.";
    }

    @Override
    public String getLongDesc() {
        return "A lightning strikes the target after a delay of " + getDelayMS() / 1000 +
                " s. It deals up to " + (int) (getPercentMaxHPDamage() * 100) +
                "% max hp damage based on target's missing health, maxed when enemies are at or below 30% hp.";
    }
}
