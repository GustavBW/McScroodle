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

        Point2D position = getOwner().getOrigin();

        for(Enemy target : targets) {
            applyAugs(new AugmentedBullet(position, target, getOwner())).spawn();
        }
    }

    @Override
    public void evaluate() {

    }

    protected Bullet applyAugs(Bullet b){
        for (Augment a : getOwner().getAugments()) {
            a.applyToBullet(b);
        }
        return b;
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
    public Invocation copy(int i){
        return new BasicDMGInvocation(Math.min(i, getMaxLevel()));
    }


    @Override
    public String getDesc(){
        return "As basic as basic comes.";
    }
}
