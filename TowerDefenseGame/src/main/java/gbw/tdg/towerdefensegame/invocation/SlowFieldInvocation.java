package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.augments.SlowEffect;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

import java.util.List;
import java.util.Set;

public class SlowFieldInvocation extends BasicSPDInvocation {

    private double fieldRange;
    private double slowPercent;

    public SlowFieldInvocation(int level) {
        super(level);
        fieldRange = getLevel() * 200 * (1 / 1920D);
        slowPercent = getLevel() * .1;
        super.setUseTowerRange(false);
        super.setSearchRange(fieldRange);
    }

    @Override
    public Invocation copy() {
        return new SlowFieldInvocation(getLevel());
    }

    @Override
    public void attack(List<Enemy> possibleTargets) {}

    @Override
    public void evaluate() {
        Set<Enemy> enemiesInRange = super.findEnemiesInRange();
        for(Enemy e : enemiesInRange){
            e.addLifetimeEffect(new SlowEffect(500,slowPercent,this));
        }
    }

    @Override
    public List<Enemy> preattack() {
        return null;
    }

    @Override
    public boolean applyToTower(Tower t) {
        return true;
    }
}
