package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

import java.util.List;
import java.util.Set;

public class MultishotInvocation extends BasicSPDInvocation{

    protected MultishotInvocation(int level) {
        super(level);
    }

    @Override
    public Invocation copy() {
        return new MultishotInvocation(this.getLevel());
    }

    @Override
    public boolean applyToTower(Tower t) {
        t.setSPDInvocation(this);
        t.setMultishot(t.getMultishot() + getLevel());
        return true;
    }

    @Override
    public String getDesc(){
        return "Allows the Tower to shoot at " + getLevel() + " additional targets at once.";
    }
}
