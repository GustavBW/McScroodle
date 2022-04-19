package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class HellfireAugment extends Augment{

    protected HellfireAugment(double value, int type, int level) {
        super(value, type, level);
    }

    @Override
    public void triggerEffects(Enemy e, Bullet b){
        double totalBurn = b.getDamage() * (0.2 * super.level) + super.level;

        e.addLifetimeEffect(
                new BurnEffect(totalBurn,3_000,this)
        );
    }
}
