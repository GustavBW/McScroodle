package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Decimals;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public class ExplosiveAugment extends Augment{

    private double explosionRadius;

    public ExplosiveAugment(double value, int type, int level){
        super(value,type,level);
        this.explosionRadius = 100 * level * (Main.canvasSize.getX() * (1.00 / 1920));
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet){
        Set<IEnemy> enemiesFound = findEnemiesInRange(enemyHit);
        for(IEnemy e : enemiesFound){
            e.onHitByBullet(bullet);
        }
    }

    private Set<IEnemy> findEnemiesInRange(IEnemy enemyHit){
        Set<IEnemy> enemiesFound = new HashSet<>();
        Point2D tPos = enemyHit.getPosition();

        for(IEnemy e : IEnemy.active){
            if(tPos.distance(e.getPosition()) <= explosionRadius){
                enemiesFound.add(e);
            }
        }
        enemiesFound.remove(enemyHit);
        return enemiesFound;
    }

    @Override
    public String getDesc(){
        return "Bullets explode in a " + Decimals.toXDecimalPlaces(explosionRadius,0) + " unit radius.";
    }
}
