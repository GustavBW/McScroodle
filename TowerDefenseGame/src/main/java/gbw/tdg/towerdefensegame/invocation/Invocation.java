package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.ClickableIcon;
import gbw.tdg.towerdefensegame.UI.Displayable;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.StatType;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Invocation implements Displayable {

    public static EmptyInvocation EMPTY = new EmptyInvocation(1);

    private static List<Invocation> dmgInvos;
    private static List<Invocation> rngInvos;
    private static List<Invocation> spdInvos;
    private static Map<StatType, List<Invocation>> statInvoListMap;

    private final static Invocation dmgBase = new BasicDMGInvocation(1);
    private final static Invocation spdBase = new BasicSPDInvocation(1);
    private static boolean contentsPrepped = false;

    private int level;
    private Tower owner;
    private ClickableIcon<Invocation> icon;
    private Image image;

    private static void prepContent(){
        Map<String, Integer> nameLevelMap = ContentEngine.TEXT.getInvocationLevels();

        if(!contentsPrepped){
            dmgInvos = new ArrayList<>(List.of(
                    //Invocations for DMG is: Shotgun, Ray, DoomRay, Spinner, Burster
                    new GrapeShotInvocation(nameLevelMap.getOrDefault("GrapeShotInvocation",1)),
                    new ComboInvocation(nameLevelMap.getOrDefault("ComboInvocation",1))
            ));
            rngInvos = new ArrayList<>(List.of(
                    new SlowFieldInvocation(nameLevelMap.getOrDefault("SlowFieldInvocation",1)),
                    new MassiveInvocation(nameLevelMap.getOrDefault("MassiveInvocation",1))
                    //Invocations for RNG is: Slowfield,
            ));
            spdInvos = new ArrayList<>(List.of(
                    //Invocations for SPD is: Multishot
                    new MultishotInvocation(nameLevelMap.getOrDefault("MultishotInvocation",1)),
                    new SniperInvocation(nameLevelMap.getOrDefault("SniperInvocation",1)),
                    new TempoInvocation(nameLevelMap.getOrDefault("TempoInvocation",1))
            ));
            statInvoListMap = new HashMap<>(Map.of(
                    StatType.DAMAGE, dmgInvos,
                    StatType.RANGE, rngInvos,
                    StatType.ATTACK_SPEED, spdInvos
            ));
            contentsPrepped = true;
        }
    }
    public static void reloadContent(){
        contentsPrepped = false;
        prepContent();
    }

    protected Invocation(int level){
        this.level = level;
    }
    public Invocation(Invocation i){
        this.level = i.getLevel();
        this.owner = i.getOwner();
        this.icon = i.getIcon();
        this.image = i.getImage();
    }

    public void setTower(Tower t){
        this.owner = t;
    }
    public Tower getOwner(){return owner;}
    public int getLevel(){return level <= 0 ? 1 : level;}
    public boolean setLevel(int i){
        this.level = Math.min(i, getMaxLevel());
        return this.level == i;
    }
    public int getMaxLevel() {
        return 101;
    }
    public ClickableIcon<Invocation> getIcon(){
        if(icon == null){
            icon = new ClickableIcon<>(getImage(),100, Point2D.ZERO,Point2D.ZERO,true,this);
        }
        return icon;
    }
    public Image getImage(){
        if(image == null){
            image = ContentEngine.INVOCATIONS.getImage(TextFormatter.getIsolatedClassName(this));
        }
        return image;
    }
    public Invocation copy(){
        return copy(getLevel());
    }
    public abstract Invocation copy(int newLevel);

    public abstract void attack(List<Enemy> possibleTargets);
    public abstract void evaluate();
    public abstract List<Enemy> preattack();
    public abstract boolean applyToTower(Tower t);

    public static Invocation getDMGBase(){
        prepContent();
        return dmgBase.copy();
    }
    public static Invocation getSPDBase(){
        prepContent();
        return spdBase.copy();
    }
    public static List<Invocation> getForStatType(StatType type, int amount){
        List<Invocation> invosToReturn = new ArrayList<>();
        prepContent();
        if(statInvoListMap.get(type) == null){
            return invosToReturn;
        }
        List<Invocation> availableForStat = statInvoListMap.get(type);
        if(availableForStat.isEmpty()){return invosToReturn;}

        int size = availableForStat.size();
        int startAt = Main.random.nextInt(0,size);

        while(invosToReturn.size() <= amount) {
            for (int i = 0; i < amount; i++) {
                invosToReturn.add(availableForStat.get((i + startAt) % size).copy());
            }
        }

        return invosToReturn;
    }
    public static Invocation getSpecific(String name){
        prepContent();
        for(StatType s : StatType.values()) {
            for (Invocation i : statInvoListMap.get(s)) {
                if (TextFormatter.getIsolatedClassName(i).equalsIgnoreCase(name)) {
                    return i.copy();
                }
            }
        }
        return null;
    }
    public static List<Invocation> getAll(){
        prepContent();
        List<Invocation> toReturn = new ArrayList<>();
        toReturn.addAll(dmgInvos);
        toReturn.addAll(spdInvos);
        toReturn.addAll(rngInvos);
        return toReturn;
    }

    public String getName(){
        String s = TextFormatter.getIsolatedClassName(this);
        int index = s.indexOf("Invocation");
        return s.substring(0,index) + " " + TextFormatter.toRomanNumerals(getLevel());
    }

    public String getDesc(){
        return "Lmao the sucker didn't impliment this for " + getName() + " Invocation";
    }
    public String getLongDesc(){
        return "Lorem Ipsum Wingardium... You know this dev is so fcking lazy he didn't even just copy paste the standard long description from Augment. Incredible";
    }


}
