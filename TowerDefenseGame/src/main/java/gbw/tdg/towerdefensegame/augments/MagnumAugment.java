package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class MagnumAugment extends Augment{


    protected MagnumAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level, maxLevel);
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {
        //Reminder to self: This damage is on top of regular bullet damage.
        enemyHit.applyDamage(bullet.getDamage() * getFactor(bullet));
    }

    @Override
    public void applyToBullet(Bullet bullet){
        bullet.setDimensions(bullet.getDimensions().multiply(1.5));
        super.applyToBullet(bullet);
    }

    private double getFactor(Bullet bullet){
        return getFactor(bullet.getFlightDistance());

    }
    private double getFactor(double dist){
        //Reminder to self: Multiplying damage multiplicatively might be a bad idea.
        //Function: ((-level * dist + level * C) / 100) + level. Linear decrease
        double d = ((-getLevel() * dist + getLevel() * (getNoDmgAt())) / 100D) + 1;
        return Math.max(d,0);
    }
    private double getNoDmgAt(){
        return 300 * Main.scale.getX();
    }
    private double getBDmgAtX0(){
        return ((getLevel() * (getNoDmgAt())) / 100D) + 1;
    }

    @Override
    public Augment copy() {
        return new MagnumAugment(getValue(),getType(),getLevel(),getMaxLevel());
    }

    @Override
    public String getDesc(){
        return "Bullets gain massive bonus damage, but it strongly decreases over distance";
    }

    @Override
    public String getLongDesc() {
        return "Bullets size are increased and they deal up to " + (getBDmgAtX0() - 1) + " times normal damage as bonus damage. However, if the target is more than " + getNoDmgAt() + " units away, the Tower gains no bonus damage and the bonus also decreases towards this point";
    }
}
