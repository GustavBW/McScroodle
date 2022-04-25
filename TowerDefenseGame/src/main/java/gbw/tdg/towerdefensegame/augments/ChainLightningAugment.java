package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class ChainLightningAugment extends Augment implements BounceReciever<Enemy,Bullet> {

    private final double baseSearchRangeMultiplier = 20, baseSearchRange = 100;
    private final int baseJumpCount = 2;

    protected ChainLightningAugment(double value, int type, int level) {
        super(value, type, level);
        needsToNotHaveRequirement = true;
        requirement = type;
        this.appliesOnHit = true;
    }

    @Override
    public Augment getModified(int level){
        needsToNotHaveRequirement = true;
        appliesOnHit = true;
        requirement = type;
        return super.getModified(level);
    }

    @Override
    public Augment copy() {
        return new ChainLightningAugment(this.getValue(), this.getType(), this.getLevel());
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet){
        new BounceBackChainLightning(enemyHit,getJumpRadius(),getJumpCount(), this, bullet).spawn();
    }

    private double getJumpRadius(){
        return ((baseSearchRangeMultiplier * level) + baseSearchRange) * (Main.canvasSize.getX() * (1.00 / 1920));
    }
    private int getJumpCount(){
        return baseJumpCount + level * 2;
    }
    private double getDamagePercentage(){
        return (20 * level) / 100.0;
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
