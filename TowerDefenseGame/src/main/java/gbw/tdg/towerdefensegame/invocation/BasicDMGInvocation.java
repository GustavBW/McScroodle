package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.AugmentedBullet;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;

import java.util.List;

public class BasicDMGInvocation extends Invocation{

    public BasicDMGInvocation(int level) {
        super(level);
    }

    public void attack(List<Enemy> targets){
        if(getOwner() == null){return;}

        Point2D position = getOwner().getPosition();
        Point2D dim = getOwner().getDimensions();

        for(Enemy target : targets) {
            Bullet b = new AugmentedBullet(position.add(dim.getX() * 0.5, dim.getY() * 0.5), target, getOwner());

            for (Augment a : getOwner().getAugments()) {
                a.applyToBullet(b);
            }
            b.spawn();
        }
    }

    @Override
    public void evaluate() {

    }

    @Override
    public List<Enemy> preattack() {
        return null;
    }

    @Override
    public boolean applyToTower(Tower t) {
        t.setDMGInvocation(this);
        return true;
    }

    @Override
    public Invocation copy(){
        return new BasicDMGInvocation(getLevel());
    }

    @Override
    public String getDesc(){
        return "As basic as basic comes.";
    }
}