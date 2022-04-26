package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.UI.vfx.TopDownStrikeVFX;
import gbw.tdg.towerdefensegame.UI.vfx.VFX;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class LightningAugment extends Augment{

    protected LightningAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level,maxLevel);
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {
        new BounceBackImpulse<>(getDelayMS(), this, enemyHit, bullet).spawn();
    }

    @Override
    public void bounce(Enemy e, Bullet b){
        e.applyDamage(e.getMaxHP() * getPercentMaxHPDamage() + b.getDamage());
        new TopDownStrikeVFX(200, VFX.DEFAULT_PRIO, e.getPosition()).spawn();
    }

    @Override
    public Augment copy() {
        return new LightningAugment(this.getValue(), this.getType(), this.getLevel(),this.getMaxLevel());
    }
    private double getPercentMaxHPDamage(){
        return (2 * Math.log10(getLevel()) + 1) / 10.0;
    }

    private int getDelayMS(){
        int toreturn = (int) ((3 * (1.0 / getLevel())) * 1000);

        return toreturn;
    }

    @Override
    public String getDesc(){
        return "A lightning striking for " + Decimals.toXDecimalPlaces(getPercentMaxHPDamage() * 100,0)
                + " % max hp damage after " + getDelayMS() / 1000 + " s";
    }

}
