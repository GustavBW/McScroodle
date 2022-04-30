package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class SlowEffect extends LifetimeEffect {

    private double slowPercentage;

    public SlowEffect(int lifetimeMS, double slowPercentage, Object owner){
        super(owner);
        super.lifetimeMS = lifetimeMS;
        this.slowPercentage = slowPercentage;
    }

    @Override
    public synchronized void evaluateOn(Enemy e){
        e.setTempMovespeed(e.getMvspeed() * slowPercentage);
        super.evalLifetime(e);
    }

    public void setSlowPercentage(double perc){
        this.slowPercentage = perc;
    }

    @Override
    public String getEffectString() {
        return "Slowed " + Decimals.toXDecimalPlaces(slowPercentage * 100,2) + "%";
    }
}
