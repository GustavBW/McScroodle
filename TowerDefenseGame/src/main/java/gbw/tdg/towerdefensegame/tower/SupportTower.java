package gbw.tdg.towerdefensegame.tower;

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
            if (System.currentTimeMillis() >= super.attackDelayMS + lastCall) {
                lastCall = System.currentTimeMillis();
                buffTowers(findTowersInRange());
            }
        }
    }

    private List<Tower> findTowersInRange(){
        List<Tower> towersInRange = new ArrayList<>();
        for(Tower t : Tower.active){
            if(t == this){continue;}
            if(super.position.distance(t.getPosition()) <= super.range){
                towersInRange.add(t);
            }

        }
        return towersInRange;
    }

    private void buffTowers(List<Tower> towers){
        for(Tower t : towers){

        }
    }
}
