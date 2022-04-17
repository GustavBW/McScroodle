package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExplosiveBullet extends Bullet{

    private double explosionRadius;

    public ExplosiveBullet(Point2D position, IEnemy target, ITower owner, double explosionRadius) {
        super(position, target,owner);
        this.explosionRadius = explosionRadius;
    }


    @Override
    protected void onCollision(IEnemy enemyHit){
        super.onCollision(enemyHit);

        for(IEnemy e : searchForNearbyEnemies(enemyHit)){
            e.onHitByBullet(this);
        }
    }

    private Set<IEnemy> searchForNearbyEnemies(IEnemy enemyHit){
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
