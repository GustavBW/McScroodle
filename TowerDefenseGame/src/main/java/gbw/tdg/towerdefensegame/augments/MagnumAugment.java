package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

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

    @Override
    public void onSuccesfullApplication(Tower t) {

    }

    private double getFactor(Bullet bullet){
        return getFactor(bullet.getFlightDistance());

    }
    private double getFactor(double dist){
        //Reminder to self: Multiplying damage multiplicatively might be a bad idea.
        //Function: ln(level * 3) / (dist + 100) * 200. Logarithmic decrease
        return (Math.log(getLevel() * 3) / dist + 100) * localScaleFactor();
    }
    private double localScaleFactor(){
        return 200 * Main.scale.getX();
    }
    private double getBDmgAtX0(){
        return getFactor(0);
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
        return "Bullets size are increased and they deal up to " + Decimals.toXDecimalPlaces(getBDmgAtX0() - 1,1)
                + " times normal damage as bonus damage. However, this bonus damage decreases the further the bullet travels. At "
                + Main.scale.getX() * 300 + " units, the bullet only deals " + Decimals.toXDecimalPlaces(getFactor(Main.scale.getX() * 300) - 1,1)
                + " times bonus damage.";
    }
}
