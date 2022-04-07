package gbw.tdg.towerdefensegame;

import java.util.ArrayList;
import java.util.List;

public class SupportTower extends Tower{

    private long lastCall;
    private SupportTowerBuff buff;

    public SupportTower(int points){
        super(points);
        buff = whatBuffDoIApply();
    }

    private SupportTowerBuff whatBuffDoIApply() {
        if(super.getDamage() == 0){
            return new TowerDamageBuff(this, 1D);
        }
        return new TowerAtkSpeedBuff(this,1D);
    }

    @Override
    public void tick(){
        if(isActive) {
            if (System.currentTimeMillis() >= super.attackDelay + lastCall) {
                lastCall = System.currentTimeMillis();
                buffTowers(findTowersInRange());
            }
        }
    }

    private List<ITower> findTowersInRange(){
        List<ITower> towersInRange = new ArrayList<>();
        for(ITower t : ITower.active){
            if(t == this){continue;}
            if(super.position.distance(t.getPosition()) <= super.range){
                towersInRange.add(t);
            }

        }
        return towersInRange;
    }

    private void buffTowers(List<ITower> towers){
        for(ITower t : towers){
            t.applyDamageBuff(buff);
        }
    }
}
