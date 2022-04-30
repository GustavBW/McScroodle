package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class BurnEffect extends LifetimeEffect {

    private double damageASecond,damageApplied;

    public BurnEffect(double totalDamage, int lifetimeMS, Object owner){
        super(owner);
        super.lifetimeMS = lifetimeMS;
        lastCall = spawnTime = System.currentTimeMillis();
        this.damageASecond = totalDamage / lifetimeMS;
    }

    @Override
    public synchronized void evaluateOn(Enemy e){
        long now = System.currentTimeMillis() +1;
        double factor = 1000.0 / (now - lastCall);
        double damageToApplyThisTick = damageASecond * factor;
        lastCall = now;

        e.applyDamage(damageToApplyThisTick);
        damageApplied += damageToApplyThisTick;

        super.evalLifetime(e);
    }

    @Override
    public String getEffectString(){
        return "Burning " + Decimals.toXDecimalPlaces(damageApplied,3);
    }

    public void setTotalDamage(double damage){
        this.damageASecond = damage / lifetimeMS;
    }

}
