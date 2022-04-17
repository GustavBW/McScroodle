package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public class ExplosiveAugment extends Augment{

    private double explosionRadius;

    public ExplosiveAugment(int level) {
        super();
        this.explosionRadius = level * 100;
        super.level = level;
        super.setChild(this);
        value = 10 * level;
    }

    public void applyToEnemy(IEnemy enemyHit,Bullet bullet){
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
}
