package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveBullet extends Bullet{

    private double explosionRadius;

    public ExplosiveBullet(Point2D position, IEnemy target, double damage, double explosionRadius) {
        super(position, target, damage);
        this.explosionRadius = explosionRadius;
    }


    @Override
    protected void onCollision(){
        for(IEnemy e : searchForNearbyEnemies(super.target)){
            e.changeHp(super.damage);
        }
    }
    private List<IEnemy> searchForNearbyEnemies(IEnemy target){
        List<IEnemy> enemiesFound = new ArrayList<>();
        Point2D tPos = target.getPosition();

        for(IEnemy e : IEnemy.active){
            if(tPos.distance(e.getPosition()) <= explosionRadius){
                enemiesFound.add(e);
            }
        }

        return enemiesFound;
    }
}
