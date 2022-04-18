package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.tower.Tower;
import gbw.tdg.towerdefensegame.UI.AugmentIcon;
import gbw.tdg.towerdefensegame.enemies.Enemy;

public abstract class Augment {

    //Creating new augments (read: instantiating) is done through calling Augment.getRandom(double) or Augment.getSpecific(int).
    //this will return one of the static Augments which itself uses the protected constructor below.
    //The way these specific augments are stored and accessed are subject to change. The way they're retrieved are not.

    private final static Augment EXPLOSIVE_I = new ExplosiveAugment(10,0,1);
    private final static Augment EXPLOSIVE_II = new ExplosiveAugment(10,0,2);
    private final static Augment EXPLOSIVE_III = new ExplosiveAugment(10,0,3);
    private final static Augment PIERCING_I = new PiercingAugment(5,1,1);
    private final static Augment PIERCING_II = new PiercingAugment(5,1,2);
    private final static Augment PIERCING_III = new PiercingAugment(5,1,3);

    protected int level;
    protected Tower tower;
    protected double value;
    protected AugmentIcon icon;
    protected Augment requirement = null;
    private boolean needsToNotHaveRequirement = false;
    protected int type;
    private final int id;
    private static int amountOfAugmentObjects = 0;

    public static Augment getRandom(double value){
        return getRandomAugment(value);
    }
    public static Augment getSpecific(int id){
        return getSpecificAugment(id);
    }

    private static Augment getRandomAugment(double value){
        System.out.println("Trying to get Augment at Augment.37 - not implimented");
        return null;
    }
    private static Augment getSpecificAugment(int id){
        System.out.println("Trying to get Augment at Augment.37 - not implimented");
        return null;
    }

    private Augment(double value){
        this(value,-1);
    }
    private Augment(double value,int type){
        this(value, type,1);
    }
    protected Augment(double value, int type, int level){
        this.value = value;
        this.type = type;
        this.level = level;
        this.id = amountOfAugmentObjects;
        amountOfAugmentObjects++;
    }

    public void applyToBullet(Bullet bullet){}

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

    public void triggerEffects(Enemy enemyHit, Bullet bullet) {}

    public String getDesc(){
        return "Unkown Augment which powers may become the subject of legend";
    }
}
