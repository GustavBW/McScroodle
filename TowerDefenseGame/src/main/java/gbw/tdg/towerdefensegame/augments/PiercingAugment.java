package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;

public class PiercingAugment extends Augment{

    protected PiercingAugment(double value, int type, int level) {
        super(value, type, level);
    }

    @Override
    public void applyToBullet(Bullet b){
        b.setPiercingLevel(level);
    }

    @Override
    public String getDesc(){
        return "Allows bullets to penetrate through enemies";
    }
}
