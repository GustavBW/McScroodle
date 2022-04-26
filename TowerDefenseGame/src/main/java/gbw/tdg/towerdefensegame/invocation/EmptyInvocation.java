package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

import java.util.List;

public class EmptyInvocation extends Invocation{


    public EmptyInvocation(int level) {
        super(level);
    }

    @Override
    public Invocation copy() {
        return null;
    }

    @Override
    public void attack(List<Enemy> possibleTargets) {

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
        return false;
    }
}
