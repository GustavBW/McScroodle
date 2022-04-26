package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.ClickableIcon;
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

public abstract class Invocation {

    public static EmptyInvocation EMPTY = new EmptyInvocation(1);

    private static final List<Invocation> dmgInvos = new ArrayList<>(List.of(
        //Invocations for DMG is: Shotgun, Ray, DoomRay, Spinner, Burster
            new BasicDMGInvocation(1)
    ));
    private static final List<Invocation> rngInvos = new ArrayList<>(List.of(
            new BasicDMGInvocation(1),
            new SlowFieldInvocation(1)
        //Invocations for RNG is: Slowfield,
    ));
    private static final List<Invocation> spdInvos = new ArrayList<>(List.of(
            //Invocations for SPD is: Multishot
            new MultishotInvocation(3),
            new BasicSPDInvocation(1)
    ));
    private static final Map<StatType, List<Invocation>> statInvoListMap = new HashMap<>(Map.of(
            StatType.DAMAGE, dmgInvos,
            StatType.RANGE, rngInvos,
            StatType.ATTACK_SPEED, spdInvos
    ));

    private final static Invocation dmgBase = new BasicDMGInvocation(1);
    private final static Invocation spdBase = new BasicSPDInvocation(1);

    private final int level;
    private Tower owner;
    private ClickableIcon<Invocation> icon;
    private Image image;

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
    public abstract Invocation copy();

    public abstract void attack(List<Enemy> possibleTargets);
    public abstract void evaluate();
    public abstract List<Enemy> preattack();
    public abstract boolean applyToTower(Tower t);

    public static Invocation getDMGBase(){
        return dmgBase.copy();
    }
    public static Invocation getSPDBase(){
        return spdBase.copy();
    }
    public static List<Invocation> getForStatType(StatType type, int amount){
        List<Invocation> invosToReturn = new ArrayList<>();
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
    public String getName(){
        String s = TextFormatter.getIsolatedClassName(this);
        int index = s.indexOf("Invocation");
        return s.substring(0,index);
    }

    public String getDesc(){
        return "Lmao the sucker didn't impliment this for " + getName() + " Invocation";
    }


}
