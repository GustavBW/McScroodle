package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;

public class AugmentedBullet extends Bullet {

    public AugmentedBullet(Point2D position, IEnemy target, double damage) {
        super(position,target,damage);
    }

    public void increaseDamage(double amount){
        super.damage += amount;
    }
    public void increaseSpeed(double amount){
        super.speed += amount;
    }

}
