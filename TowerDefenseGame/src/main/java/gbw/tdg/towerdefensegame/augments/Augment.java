package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.Displayable;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.tower.Tower;
import gbw.tdg.towerdefensegame.UI.ClickableIcon;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Augment implements Comparable<Augment>, BounceReciever<Enemy,Bullet>, Displayable {

    //Creating new augments (read: instantiating) is done through calling Augment.getRandom(double) or Augment.getSpecific(int).
    //this will return one of the static Augments which itself uses the protected constructor below.
    //These Augments below are loaded through the Augment.getAugs() method.

    private static List<Augment> contents;

    private int level, maxLevel = 3;
    protected Tower tower;
    private double value;
    private ClickableIcon icon;
    private Image image;
    protected int requirement = -1;
    protected boolean needsToNotHaveRequirement = false, appliesOnHit = false;
    private int type;
    private final int id;
    private static int amountOfAugmentObjects = 0;
    private static boolean contentPrepped = false;

    public static Augment getRandom(double value){
        return getRandomAugment(value);
    }
    public static Augment getSpecific(int type, int level){
        return getSpecificAugment(type,level);
    }

    private static void prepContent(){
        if(!contentPrepped) {
            Map<String, Integer> levelMap = ContentEngine.TEXT.getAugmentStartingLevels();

            contents = new ArrayList<>(List.of(
                    new ExplosiveAugment(10, 0, levelMap.getOrDefault("ExplosiveAugment", 1), 5),
                    new PiercingAugment(5, 1, levelMap.getOrDefault("PiercingAugment", 1), 10),
                    new HellfireAugment(3, 2, levelMap.getOrDefault("HellfireAugment", 1), 20),
                    new IceAugment(3, 3, levelMap.getOrDefault("IceAugment", 1), 20),
                    new ChainLightningAugment(10, 4, levelMap.getOrDefault("ChainLightningAugment", 1), 5),
                    new LightningAugment(8, 5, levelMap.getOrDefault("LightningAugment", 1), 3),
                    new VelocityAugment(3, 6, levelMap.getOrDefault("VelocityAugment", 1), 20),
                    new MagnumAugment(5, 7, levelMap.getOrDefault("MagnumAugment", 1), 20),
                    new DamageUpAugment(3, 8, levelMap.getOrDefault("DamageUpAugment", 1), 20),
                    new SpeedUPAugment(3, 9, levelMap.getOrDefault("SpeedUpAugment", 1), 20),
                    new RangeUpAugment(3, 10, levelMap.getOrDefault("RangeUpAugment", 1), 20)
            ));

            contentPrepped = true;
        }
    }
    public static void reloadContent(){
        contentPrepped = false;
        prepContent();
    }

    private static Augment getRandomAugment(double value){
        List<Augment> augs = getAugs();

        int size = augs.size();

        Augment current = augs.get(Main.random.nextInt(0,size) % size);
        double eValue = current.getValue();

        for(int j = current.getMaxLevel(); j > current.getLevel(); j--) {

            double worthAtLevelJ = eValue * j;

            if (isValInRange(value, worthAtLevelJ * 0.7, worthAtLevelJ * 1.3)) {
                return current.getModified(j);
            }
        }

        return current.copy();
    }
    private static boolean isValInRange(double val, double min, double max){
        return val >= min && val <= max;
    }
    public static List<Augment> getAugs(){
        prepContent();
        return contents;
    }
    private static Augment getSpecificAugment(int type, int level){
        for(Augment a : getAugs()){
            if(a.getType() == type){
                return a.getModified(level);
            }
        }

        return null;
    }

    protected Augment(double value, int type, int level){
        this.value = value;
        this.type = type;
        this.level = level;
        this.id = amountOfAugmentObjects;
        amountOfAugmentObjects++;
    }
    protected Augment(double value, int type, int level, int maxLevel){
        this(value, type, level);
        this.maxLevel = maxLevel;
    }

    public void applyToBullet(Bullet bullet){
        bullet.addOnHitAug(this);
    }

    public boolean applyToTower(Tower tower){
        boolean success = false;
        if(requirement != -1){
            if(!needsToNotHaveRequirement) {
                for (Augment a : tower.getAugments()) {
                    if (a.getType() == requirement) {
                        this.tower = tower;
                        success = true;
                        break;
                    }
                }
            }else{
                boolean hasIt = false;
                for(Augment a : tower.getAugments()){
                    if(a.getType() == requirement){
                        hasIt = true;
                        break;
                    }
                }
                if(!hasIt){
                    this.tower = tower;
                    success = true;
                }
            }
        }else {
            this.tower = tower;
            success = true;
        }

        if(success){
            onSuccesfullApplication(tower);
        }

        return success;
    }
    public abstract void onSuccesfullApplication(Tower t);
    public abstract void triggerEffects(Enemy enemyHit, Bullet bullet);

    public int getType(){
        return type;
    }
    public Tower getOwner(){return tower;}
    public int getId(){return id;}
    public double getWorth(){
        return level * value;
    }
    public double getValue(){
        return value;
    }
    public int getMaxLevel(){
        return maxLevel;
    }
    public boolean setLevel(int i){
        if(i > maxLevel){
            return false;
        }
        int prev = this.level;
        this.level = i;
        if(prev < level){
            onLevelUp();
        }
        return true;
    }
    public void onLevelUp(){}
    public void setMaxLevel(int ml){this.maxLevel = ml;}
    public int getLevel(){
        return level <= 0 ? 1 : level;
    }
    public String getDesc(){
        return "Unkown Augment which powers may become the subject of legend";
    }
    public String getLongDesc(){
        return "Lorem Ipsum Wingardium... Fuck me can't remember the damn thing. Well, having a nice day? Don't worry 'bout this, "
                + " some dev clearly messed up and his farther will hear about this. It's just a default placeholder, nothing "
                + " to worry about. Nothing at all. The game wont crash. The CPU isn't on fire. We aren't in danger. Everything is well";
    }
    public String getName(){
        String base = this.toString();
        int index = base.indexOf('@')-7;
        return base.substring(0,index) + " " + TextFormatter.toRomanNumerals(this.getLevel());
    }
    public Image getImage(){
        if(image != null){
            return image;
        }
        image = ContentEngine.AUGMENTS.getImage(TextFormatter.getIsolatedClassName(this));
        return image;
    }
    public ClickableIcon<Augment> getIcon(){
        if(icon == null){
            this.icon = new ClickableIcon<>(getImage(),100, Point2D.ZERO,Point2D.ZERO,true,this);
        }
        return icon;
    }
    public boolean appliesOnHit(){
        return appliesOnHit;
    }
    @Override
    public void bounce(Enemy e, Bullet b){}
    public String baseStats(){
        return TextFormatter.getIsolatedClassName(this) + " val " + getValue() + " level " + getLevel() + " worth " + getWorth();
    }
    public Augment getModified(int level){
        Augment newAug = this.copy();
        newAug.setLevel(Math.min(level, this.maxLevel));
        return newAug;
    }

    public abstract Augment copy();

    @Override
    public String toString(){
        String superStr = super.toString();
        int index = superStr.lastIndexOf('.') + 1;

        String ownerStr = tower == null ? "null" : tower.toString();
        int index2 = tower == null ? 0 : ownerStr.lastIndexOf('.') + 1;
        return superStr.substring(index) + " owner: " + ownerStr.substring(index2);
    }

    @Override
    public int compareTo(Augment a){
        return Integer.compare(this.id,a.getId());
    }
}
