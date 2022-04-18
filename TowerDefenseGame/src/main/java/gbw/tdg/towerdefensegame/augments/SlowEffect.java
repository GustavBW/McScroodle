package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.LifetimeEffect;

public class SlowEffect extends LifetimeEffect {

    private double slowPercentage;

    public SlowEffect(int lifetimeMS, double slowPercentage){
        super.lifetimeMS = lifetimeMS;
        this.slowPercentage = slowPercentage;
    }

    @Override
    public void evaluateOn(Enemy e){
        e.setMvspeed(e.getMvspeed() * slowPercentage);
    }


    @Override
    public String getEffectString() {
        return "Slowed " + slowPercentage + "%";
    }
}
