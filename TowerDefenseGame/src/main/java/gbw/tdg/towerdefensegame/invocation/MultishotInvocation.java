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
    public Invocation copy(int i){
        return new MultishotInvocation(Math.min(i, getMaxLevel()));
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

    @Override
    public String getLongDesc(){
        return "The Tower will attack up to " + getLevel() + " additional targets when firing. These additional shots does apply on-hit effects and on-hit sources like Chain Lightning Augment and Explosive Augment";
    }

}
