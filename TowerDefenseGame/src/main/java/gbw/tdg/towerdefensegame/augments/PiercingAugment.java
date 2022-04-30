package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class PiercingAugment extends Augment{

    protected PiercingAugment(double value, int type, int level, int maxLevel) {
        super(value, type, level,maxLevel);
    }

    @Override
    public void applyToBullet(Bullet b){
        b.addPiercingLevels(getLevel());
    }

    @Override
    public void triggerEffects(Enemy enemyHit, Bullet bullet) {}

    @Override
    public String getDesc(){
        return "Allows bullets to penetrate through " + getLevel() + " enemies";
    }

    @Override
    public String getLongDesc(){
        return "Allows bullets to penetrate through " + getLevel() + " enemies";
    }


    @Override
    public Augment copy() {
        return new PiercingAugment(this.getValue(), this.getType(), this.getLevel(),this.getMaxLevel());
    }

    @Override
    public void bounce(Enemy e, Bullet b) {

    }
}
