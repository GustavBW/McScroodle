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
        return getLevel() / 10D;
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
        return "The Tower attacks once every " + (1_000 / getNewSpeed()) / 1_000 + " s. But damage is greatly increased.";
    }

    @Override
    public String getLongDesc(){
        return "The Tower attacks once every " + (1_000 / getNewSpeed()) / 1_000 + " s. But Tower base damage is multiplied by the attack speed lost plus 100%, and then further increased by this amount.";
    }
}
