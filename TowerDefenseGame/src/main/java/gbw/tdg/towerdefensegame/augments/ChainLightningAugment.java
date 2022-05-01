package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class ChainLightningAugment extends Augment implements BounceReciever<Enemy,Bullet> {

    private final double baseSearchRangeMultiplier = 10, baseSearchRange = 100;
    private final int baseJumpCount = 2;

    protected ChainLightningAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level, maxLevel);
        needsToNotHaveRequirement = true;
        requirement = type;
        this.appliesOnHit = true;
    }

    @Override
    public void onSuccesfullApplication(Tower t) {

    }

    @Override
    public Augment copy() {
        return new ChainLightningAugment(this.getValue(), this.getType(), this.getLevel(),this.getMaxLevel());
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet){
        new BounceBackChainLightning(enemyHit,getJumpRadius(),getJumpCount(), this, bullet)
                .setLifetime(500).spawn();
    }

    private double getJumpRadius(){
        return ((baseSearchRangeMultiplier * getLevel()) + baseSearchRange) * Main.scale.getX();
    }
    private int getJumpCount(){
        return baseJumpCount + getLevel() * 2;
    }
    private double getDamagePercentage(){
        return (20 * getLevel()) / 100.0;
    }

    @Override
    public String getDesc(){
        return "A lightning strikes up to " + getJumpCount() + " nearby enemies for " + (getDamagePercentage() * 100)  + "% of damage";
    }

    @Override
    public String getLongDesc(){
        return "Arcs of electricity tears through up to " + getJumpCount() + " nearby enemies dealing " + (getDamagePercentage() * 100) + "% of bullet damage. The chain-lightning will only hit each enemy once and fail at jumping if the nearest, fresh enemy is further than "  + getJumpRadius() + " units away.";
    }

    @Override
    public void bounce(Enemy enemy, Bullet bullet) {
        enemy.applyDamage(bullet.getDamage() * getDamagePercentage());
        enemy.onHitByOnHit(this,bullet);
    }
}
