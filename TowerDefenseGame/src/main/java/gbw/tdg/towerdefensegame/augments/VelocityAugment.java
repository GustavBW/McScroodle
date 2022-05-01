package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

public class VelocityAugment extends Augment{

    protected VelocityAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level, maxLevel);
    }

    @Override
    public void onSuccesfullApplication(Tower t) {

    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {
        //Reminder to self: This damage is on top of regular bullet damage.
        enemyHit.applyDamage(bullet.getDamage() * getFactor(bullet));
    }

    private double getFactor(Bullet bullet){
        return getFactor(bullet.getFlightDistance());

    }
    private double getFactor(double dist){
        //Reminder to self: Multiplying damage multiplicatively might be a bad idea.
        //Function: (level / 3) * ln(x / maxDist) + level, Logarithmic growth
        double d = (getLevel() / 3D) * Math.log(dist / Main.canvasSize.getX()) + getLevel();
        return Math.max(d,0);
    }

    @Override
    public Augment copy() {
        return new VelocityAugment(getValue(),getType(),getLevel(),getMaxLevel());
    }

    @Override
    public String getDesc(){
        return "Bullets deal bonus damage based on distance flown. Bonus dmg for 1k flown: " + (int) (getFactor(1000) * 10) + " %";
    }

    @Override
    public String getLongDesc() {
        return "Bullets deal extra damage on hit if they've traveled further than " + 200 * Main.scale.getX()
                + " units. Generally double damage is achieved at around " + 1000 * Main.scale.getX()
                + " units and there is no restriction on how much bonus damage can be achieved this way.";
    }

    @Override
    public String getName(){
        return "Perpetual Velocity " + TextFormatter.toRomanNumerals(getLevel());
    }
}
