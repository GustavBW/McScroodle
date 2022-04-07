package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;

public class AugmentedBullet extends Bullet {

    public AugmentedBullet(Point2D position, IEnemy target, double damage, ITower owner) {
        super(position,target,damage,owner);
    }

    public void increaseDamage(double amount){
        super.damage += amount;
    }
    public void increaseSpeed(double amount){
        super.speed += amount;
    }

}
