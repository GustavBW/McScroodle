package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Decimals;
import gbw.tdg.towerdefensegame.UI.vfx.TopDownStrikeVFX;
import gbw.tdg.towerdefensegame.UI.vfx.VFX;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class LightningAugment extends Augment{

    protected LightningAugment(double value, int type, int level) {
        super(value, type, level);
        maxLevel = 3;
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
        return new LightningAugment(this.getValue(), this.getType(), this.getLevel());
    }
    private double getPercentMaxHPDamage(){
        return super.getLevel() / 10.0;
    }
    private int getDelayMS(){
        return (3 * (3 / getLevel())) * 1000;
    }

    @Override
    public String getDesc(){
        return "A lightning striking for " + (getPercentMaxHPDamage() * 100)
                + " % max hp damage after " + getDelayMS() / 1000 + " s";
    }

}
