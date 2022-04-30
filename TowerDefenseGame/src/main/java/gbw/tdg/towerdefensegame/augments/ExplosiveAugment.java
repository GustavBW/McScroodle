package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.vfx.CircleVFX;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;

import java.util.*;

public class ExplosiveAugment extends Augment{

    private double baseRangeMultiplier = 200;

    public ExplosiveAugment(double value, int type, int level,int maxLevel){
        super(value,type,level,maxLevel);
        needsToNotHaveRequirement = true;
        appliesOnHit = true;
        requirement = type;
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet){
        new CircleVFX(1_000,70,enemyHit.getPosition(),getERadius()).spawn();

        Set<IEnemy> enemiesFound = findEnemiesInRange(enemyHit);
        for(IEnemy e : enemiesFound){
            ((Enemy) e).onHitByOnHit(this,bullet);
            ((Enemy) e).applyDamage(bullet.getDamage() * 0.7);
        }
    }

    private double getBonusDamagePercent(){
        return getLevel() / 10D;
    }

    private Set<IEnemy> findEnemiesInRange(IEnemy enemyHit){
        Set<IEnemy> enemiesFound = new HashSet<>();
        Point2D tPos = enemyHit.getPosition();

        for(IEnemy e : IEnemy.active){
            if(tPos.distance(e.getPosition()) <= getERadius()){
                enemiesFound.add(e);
            }
        }
        enemiesFound.remove(enemyHit);
        return enemiesFound;
    }

    private double getERadius(){
        return baseRangeMultiplier + 100 * Math.log(getLevel()) * Main.scale.getX();
    }

    @Override
    public String getDesc(){
        return "Bullets explode in a " + (int) getERadius() + " unit radius.";
    }

    @Override
    public String getLongDesc(){
        return "Bullets explode on contact dealing " + getBonusDamagePercent() * 100 + "% bonus damage to all enemies within a " + (int) getERadius() + " unit radius. This explosion applies on-hit.";
    }

    @Override
    public Augment copy() {
        return new ExplosiveAugment(this.getValue(), this.getType(), this.getLevel(),this.getMaxLevel());
    }

}
