package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.LifetimeEffect;

public class BurnEffect extends LifetimeEffect {

    private double damageASecond,damageApplied;


    public BurnEffect(double totalDamage, int lifetimeMS){
        super.lifetimeMS = lifetimeMS;
        lastCall = spawnTime = System.currentTimeMillis();
        this.damageASecond = totalDamage / lifetimeMS;
    }

    @Override
    public void evaluateOn(Enemy e){
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
        double temp = damageApplied * 100;
        int dmgTwoDec = (int) (temp);
        dmgTwoDec /= 100;

        return "Burning " + dmgTwoDec;
    }

}
