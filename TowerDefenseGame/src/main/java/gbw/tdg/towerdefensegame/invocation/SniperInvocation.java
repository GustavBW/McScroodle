package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.tower.Tower;

public class SniperInvocation extends BasicSPDInvocation{

    public SniperInvocation(int level) {
        super(level);
    }


    @Override
    public Invocation copy(){
        return new SniperInvocation(getLevel());
    }

    private double getNewSpeed(){
        return .1;
    }

    @Override
    public boolean applyToTower(Tower t){
        double atkspdRemoved = t.getAtkSpeed() - getNewSpeed();
        t.setAtkSpeed(getNewSpeed());
        t.setDamage(t.getDamage() * (1 + atkspdRemoved) + atkspdRemoved);
        return super.applyToTower(t);
    }

    @Override
    public String getDesc(){
        return "Tower attacks once every " + (1_000 / getNewSpeed()) / 1_000 + " s. But damage is multiplied by the difference.";
    }

    @Override
    public String getLongDesc(){
        return "Tower attacks once every " + (1_000 / getNewSpeed()) / 1_000 + " s. But damage is multiplied by the difference + 1 and then further increased by the difference.";
    }
}
