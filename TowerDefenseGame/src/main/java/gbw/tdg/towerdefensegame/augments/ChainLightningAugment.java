package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class ChainLightningAugment extends Augment implements BounceReciever<Enemy,Bullet> {

    private final double baseSearchRangeMultiplier = 20, baseSearchRange = 100;
    private final int baseJumpCount = 2;

    protected ChainLightningAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level, maxLevel);
        needsToNotHaveRequirement = true;
        requirement = type;
        this.appliesOnHit = true;
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
        return ((baseSearchRangeMultiplier * getLevel()) + baseSearchRange) * (Main.canvasSize.getX() * (1.00 / 1920));
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
    public void bounce(Enemy enemy, Bullet bullet) {
        enemy.applyDamage(bullet.getDamage() * getDamagePercentage());
        enemy.onHitByOnHit(this,bullet);
    }
}
