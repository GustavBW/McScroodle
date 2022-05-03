package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.augments.BounceReciever;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;

public class BounceBackBullet extends Bullet {

    private BounceReciever<Enemy,Bullet> reciever;

    public BounceBackBullet(Point2D position, Enemy target, Tower owner, BounceReciever<Enemy,Bullet> reciever) {
        super(position, target, owner);
        this.reciever = reciever;
    }

    public BounceBackBullet(Point2D position, Point2D velocity, Tower owner, BounceReciever<Enemy,Bullet> reciever) {
        super(position, velocity, owner);
        this.reciever = reciever;
    }

    @Override
    protected void onCollision(Enemy e){
        super.onCollision(e);
        reciever.bounce(e,this);
    }

}
