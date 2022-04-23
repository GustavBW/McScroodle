package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class LightningAugment extends Augment {

    private final double baseSearchRangeMultiplier = 20, baseSearchRange = 100;
    private final int baseJumpCount = 2;

    protected LightningAugment(double value, int type, int level) {
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
    public void triggerEffects(Enemy enemyHit, Bullet bullet){
        new BounceBackLightning(enemyHit,getJumpRadius(),getJumpCount(), this, bullet).spawn();
    }

    public void onEnemyHitByLightning(Enemy e, Bullet b){
        e.applyDamage(b.getDamage() * getDamagePercentage());
        e.onHitByOnHit(this,b);
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

}
