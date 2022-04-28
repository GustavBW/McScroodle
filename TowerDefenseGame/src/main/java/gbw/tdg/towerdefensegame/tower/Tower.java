package gbw.tdg.towerdefensegame.tower;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.invocation.BasicDMGInvocation;
import gbw.tdg.towerdefensegame.invocation.BasicSPDInvocation;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.*;

public class Tower extends ITower{

    public static int MAX_UPGRADE_LEVEL = 10;
    private double renderingPriority = 55D, rangeMultiplier = 100;
    private double damage = 0.1, atkSpeed = 0.1;
    private int multishot = 1;
    protected double range = 0.5, attackDelayMS;
    protected boolean isSelected = false, isActive = true;
    protected Point2D position;
    private TargetingType targetingType = TargetingType.FIRST;
    private long lastCall = 0;
    private final TowerDisplay display;
    private final List<Augment> augments = new ArrayList<>();
    private final Map<StatType,Integer> upgradeMap = new HashMap<>();
    private final double maxAugments = 3, maxInvocations = 3;
    private Invocation dmgInvocation = Invocation.getDMGBase();
    private Invocation spdInvocation = Invocation.getSPDBase();
    private Invocation rngInvocation = Invocation.EMPTY;

    public Tower(int points){
        super(Point2D.ZERO,40,40);
        double subDivider = 0.01;
        double pointsSubbed = points / subDivider;

        this.position = new Point2D(0,0);

        double allocPoints;

        damage += (allocPoints = Main.random.nextDouble() * pointsSubbed) * subDivider * 100;
        damage *= 0.01;
        pointsSubbed -= allocPoints;

        range += (allocPoints = Main.random.nextDouble() * pointsSubbed) * subDivider * 100;
        range *= 0.01;
        pointsSubbed -= allocPoints;

        atkSpeed += Main.random.nextDouble() * pointsSubbed * subDivider * 100;
        atkSpeed *= 0.01;

        this.display = new TowerDisplay(this);
        attackDelayMS = 1_000 / atkSpeed;

        Invocation.getDMGBase().applyToTower(this);
        Invocation.getSPDBase().applyToTower(this);
        loadUpgradeMap();
    }

    private void loadUpgradeMap() {
        upgradeMap.put(StatType.DAMAGE,1);
        upgradeMap.put(StatType.RANGE,1);
        upgradeMap.put(StatType.ATTACK_SPEED,1);
    }

    public Tower(Point2D pos, double dmg, double atkspd, double range, int multishot){
        this(1);
        setPosition(pos);
        setAtkSpeed(atkspd);
        damage = dmg;
        this.range = range / rangeMultiplier;
        this.multishot = multishot;
    }

    public void tick(){
        if(isActive){
            rngInvocation.evaluate();

            if(lastCall + attackDelayMS < System.currentTimeMillis()){

                List<Enemy> targetsPrioritized = spdInvocation.preattack();

                if(!targetsPrioritized.isEmpty()) {
                    dmgInvocation.attack(targetsPrioritized.subList(0,Math.min(multishot, targetsPrioritized.size())));
                    lastCall = System.currentTimeMillis();
                }else{
                    lastCall += 100;
                }
            }
        }
    }
    public int upgrade(StatType type, double val){
        boolean possible = upgradeMap.getOrDefault(type, 1) <= MAX_UPGRADE_LEVEL;

        if(!possible){return -1;}
        if(Main.getGold() < getUpgradeCost()){
            new OnScreenWarning("Not enough funds", Main.canvasSize.multiply(0.5),3).spawn();
            return 0;
        }

        switch (type) {
            case RANGE -> {
                setRange(val);
                display.getRangeIndicator().setDimensions(new Point2D(getRange(),0));
            }

            case DAMAGE -> damage = val;

            case ATTACK_SPEED -> setAtkSpeed(val);
        }
        upgradeMap.put(type, upgradeMap.getOrDefault(type,0) + 1);
        return upgradeMap.get(type);
    }

    public double getRange(){
        return range * rangeMultiplier;
    }
    public double getRangeBase(){
        return range;
    }
    public void setRange(double val){
        this.range = val;
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.BLUE);
        gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);
    }
    public void setActive(boolean newState){
        this.isActive = newState;
    }
    @Override
    public double getDamage(){return damage;}
    public double getAtkSpeed(){return atkSpeed;}
    public int getMultishot(){return multishot;}
    public void setMultishot(int val){multishot = val;}
    public boolean addAugment(Augment augment){
        boolean success = false;
        if(augments.size() < maxAugments){
            if(augment.applyToTower(this)) {
                augments.add(augment);
                success = true;
                display.getStatDisplay().addNewAugment(augment.getIcon());
            }else{
                new OnScreenWarning("Augmentation Failed! - Requirements Not Met", Main.canvasSize.multiply(0.4),3).spawn();
            }
        }else {
            new OnScreenWarning("Augmentation Failed!", Main.canvasSize.multiply(0.4), 3).spawn();
        }
        return success;
    }
    public void setSPDInvocation(BasicSPDInvocation invocation){
        this.spdInvocation = invocation;
        invocation.setTower(this);
    }
    public void setDMGInvocation(BasicDMGInvocation invocation){
        this.dmgInvocation = invocation;
        invocation.setTower(this);
    }
    public void setRNGInvocation(Invocation invocation){
        this.rngInvocation = invocation;
        invocation.setTower(this);
        System.out.println("Tower: setRngInvocation using " + invocation.getName() + " for " + this);
    }
    public List<Augment> getAugments(){return augments;}

    public double setAtkSpeed(double newSpeed){
        this.atkSpeed = newSpeed;
        this.attackDelayMS = 1_000 / atkSpeed;
        return atkSpeed;
    }
    @Override
    public Point2D getPosition() {
        return position;
    }
    public Point2D getOrigin(){
        return this.position.add(sizeX * .5,sizeY * .5);
    }
    public TowerDisplay getDisplay(){return display;}
    @Override
    public void setPosition(Point2D newPos){
        position = newPos;
        display.setPosition(newPos);
    }
    public Point2D getDimensions(){
        return new Point2D(sizeX, sizeY);
    }
    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }
    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
        display.setRenderingPriority(newPrio);
    }

    public void setTargetingType(TargetingType type){
        this.targetingType = type;
    }

    public TargetingType getTargetingType(){return targetingType;}

    @Override
    public void spawn() {
        Clickable.newborn.add(this);
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
        ITower.newborn.add(this);
    }
    @Override
    public void destroy(){
        ITower.expended.add(this);
        Tickable.expended.add(this);
        Renderable.expended.add(this);
        Clickable.expended.add(this);
        display.destroy();
    }
    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() > position.getX() && pos.getX() < position.getX() + sizeX) &&
                (pos.getY() > position.getY() && pos.getY() < position.getY() + sizeY);
    }

    @Override
    public void onClick(MouseEvent event) {
        display.spawn();
    }
    @Override
    public void deselect(){
        display.destroy();
    }

    public void sell() {
        destroy();
        Main.alterGoldAmount(getWorth());
    }

    @Override
    public Clickable getRoot(){
        return this;
    }

    public String getStats(){
        return "DMG: " + TextFormatter.doubleToKMBNotation(getDamage(),1) + "\n" +
                "RNG " + TextFormatter.doubleToKMBNotation(getRange(),1) + "\n" +
                "SPD " + TextFormatter.doubleToKMBNotation(getAtkSpeed(),1) + "\n" +
                "Targets: " + targetingType.asString;
    }

    @Override
    public String toString(){
        String superStr = super.toString();
        int index = superStr.lastIndexOf('.') + 1;
        return superStr.substring(index);
    }

    public double getWorth(){
        return damage + range + atkSpeed;
    }

    public double getUpgradeCost() {
        return getWorth() + upgradeMap.getOrDefault(StatType.DAMAGE,0)
                + upgradeMap.getOrDefault(StatType.RANGE,0)
                + upgradeMap.getOrDefault(StatType.ATTACK_SPEED,0);
    }
}
