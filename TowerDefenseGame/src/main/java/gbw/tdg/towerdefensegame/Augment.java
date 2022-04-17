package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.AugmentIcon;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public class Augment {

    //This class is both a wrapper class for itself and the super class of actual augments.
    //When this class is instantiated with any parameters, it will create an object that creates the actual augment e.g. ExplosiveAugment, which will make its
    //own parent Augment object and then call setChild on this, first object.
    //That child now has it's own parent Augment object using the no-args constructor, which is not the first one, and this parent object holds the associated
    //info like value and icon.
    //In short: For 1 actual augment (e.g. ExplosiveAugment) there is 2 Augment objects: The wrapper object, and the actual parent object.
    //When methods are called, the wrapper is called, which calls the child, which calls it's parent depending on the call.

    protected int level;
    protected Tower tower;
    protected double value;
    protected AugmentIcon icon;
    protected Augment requirement = null;
    private boolean needsToNotHaveRequirement = false;
    protected Augment child;

    public Augment(double value){
        this(value,-1);
    }
    public Augment(double value,int id){
        this.value = value;
        this.child = whatAmI(id);
        this.icon = child.icon;
    }
    protected Augment(){

    }

    protected void setValue(double val){this.value = val;}
    protected void setChild(Augment child){
        this.child = child;
        setValue(child.value);
        setIcon(child.icon);
    }
    protected void setIcon(AugmentIcon icon){this.icon = icon;}
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

    public void applyToBullet(Bullet bullet){
        child.applyToBullet(bullet);
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

    public void applyToEnemy(Enemy enemyHit,Bullet bullet) {
        child.applyToEnemy(enemyHit,bullet);
    }
}
