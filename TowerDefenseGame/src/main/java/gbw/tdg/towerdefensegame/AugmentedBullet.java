package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;

public class AugmentedBullet extends Bullet {

    public AugmentedBullet(Point2D position, Enemy target, Tower owner) {
        super(position,target,owner);
    }

    public void increaseDamage(double amount){
        super.damage += amount;
    }
    public void increaseSpeed(double amount){
        super.speed += amount;
    }

}
