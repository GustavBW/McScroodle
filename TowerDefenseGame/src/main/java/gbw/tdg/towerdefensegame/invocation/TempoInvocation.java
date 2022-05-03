package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.tower.Tower;

public class TempoInvocation extends BasicSPDInvocation implements Tickable {

    private int phase = 0;  //1 for start in double phase.
    //0 double, 1 half
    private long phaseStartTime, currentPhaseDuration;
    private final int halfPhaseDuration = 5_000;


    public TempoInvocation(int level) {
        super(level);
    }


    @Override
    public void tick() {
        if(System.currentTimeMillis() >= phaseStartTime + currentPhaseDuration){
            onPhaseChange();
        }
    }

    private void onPhaseChange() {
        Tower owner = getOwner();
        if(phase == 0){
            //Going to half time
            currentPhaseDuration = halfPhaseDuration;
            owner.setAtkSpeed(owner.getAtkSpeed() * 0.25);
            phase = 1;
        }else{
            //Going to double time
            owner.setAtkSpeed(owner.getAtkSpeed() * 4);
            currentPhaseDuration = getDoublePhaseDuration();
            phase = 0;
        }
        phaseStartTime = System.currentTimeMillis();
    }

    private void onFirstPhaseChange() {
        phaseStartTime = System.currentTimeMillis();
        currentPhaseDuration = getDoublePhaseDuration();
        getOwner().setAtkSpeed(getOwner().getAtkSpeed() * 2);
        phase = 0;
    }


    private int getDoublePhaseDuration(){
        return halfPhaseDuration + (getLevel() * 1_000);
    }


    @Override
    public Invocation copy(int i){
        return new TempoInvocation(Math.min(i, getMaxLevel()));
    }

    @Override
    public boolean applyToTower(Tower t){
        boolean success = super.applyToTower(t);
        onFirstPhaseChange();
        spawn();
        return success;
    }


    @Override
    public String getDesc(){
        return "Every 5 seconds, the Tower switches between doubling its attack speed and halving it";
    }

    @Override
    public String getLongDesc(){
        return "Every 5 seconds, the Tower switches between 2 phases: Doubling its attack speed and halving it. For each level, the doubling phase is extended by 1 second.";
    }

    @Override
    public String getName(){
        return "Lethal Tempo " + TextFormatter.toRomanNumerals(getLevel());
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }


}
