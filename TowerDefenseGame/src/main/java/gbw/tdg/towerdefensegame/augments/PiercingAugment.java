package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class PiercingAugment extends Augment{

    protected PiercingAugment(double value, int type, int level) {
        super(value, type, level);
    }

    @Override
    public void applyToBullet(Bullet b){
        b.addPiercingLevels(super.level);
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {}

    @Override
    public String getDesc(){
        return "Allows bullets to penetrate through " + super.level + " enemies";
    }


    @Override
    public Augment copy() {
        return new PiercingAugment(this.getValue(), this.getType(), this.getLevel());
    }

    @Override
    public void bounce(Enemy e, Bullet b) {

    }
}
