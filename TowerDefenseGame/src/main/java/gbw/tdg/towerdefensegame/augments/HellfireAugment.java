package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class HellfireAugment extends Augment{

    private double totalBurn;
    private final int durationMS = 3_000;

    protected HellfireAugment(double value, int type, int level) {
        super(value, type, level);
    }

    @Override
    public void triggerEffects(Enemy e, Bullet b){
        totalBurn = b.getDamage() * (0.2 * super.level) + super.level;

        e.addLifetimeEffect(
                new BurnEffect(totalBurn,durationMS,this)
        );
    }

    @Override
    public String getDesc(){
        return "Blazing bullets dealing " + Decimals.toXDecimalPlaces(100 * 0.2 * super.level,0)
                + "% of bullet damage + " + super.level + " over " + (durationMS / 1_000) + " seconds";
    }

    @Override
    public Augment copy() {
        return new HellfireAugment(this.getValue(), this.getType(), this.getLevel());
    }
}
