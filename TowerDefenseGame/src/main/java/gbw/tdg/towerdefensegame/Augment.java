package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.AugmentIcon;

public abstract class Augment {

    protected Tower tower;
    protected final double value;
    protected final AugmentIcon icon;
    protected Augment requirement = null;
    private boolean needsToNotHaveRequirement = false;
    protected final Augment child;

    public Augment(double value, AugmentIcon icon){
        this(value,icon,-1);
    }
    public Augment(double value, AugmentIcon icon, int id){
        this.value = value;
        this.icon = icon;
        this.child = whatAmI(id);
    }

    private Augment whatAmI(int id){
        if(id <= -1){
            return getRandomAugment();
        }
        Augment specificAugment = getSpecificAugment(id);
        if(specificAugment != null){
            return specificAugment;
        }
        return getRandomAugment();
    }

    private Augment whatAmI(){
        return whatAmI(-1);
    }
    private Augment getRandomAugment(){
        System.out.println("Trying to get Augment at Augment.37 - not implimented");
        return null;
    }
    private Augment getSpecificAugment(int id){
        System.out.println("Trying to get Augment at Augment.37 - not implimented");
        return null;
    }

    public void applyTo(Bullet bullet){
        child.applyTo(bullet);
    }

    public boolean applyToTower(Tower tower){
        if(requirement != null){
            if(!needsToNotHaveRequirement) {
                for (Augment a : tower.getAugments()) {
                    if (a == requirement) {
                        this.tower = tower;
                        return true;
                    }
                }
            }else{
                boolean hasIt = false;
                for(Augment a : tower.getAugments()){
                    if(a == requirement){
                        hasIt = true;
                    }
                }
                if(!hasIt){
                    this.tower = tower;
                    return true;
                }
            }
        }else {
            this.tower = tower;
            return true;
        }
        return false;
    }
}
