package gbw.tdg.towerdefensegame.tower;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class Tower implements Clickable, Tickable, ITower{

    private double renderingPriority = 55D, rangeMultiplier = 100;
    private double sizeX, sizeY, damage = 0.1, atkSpeed = 0.1;
    private int multishot = 1;
    protected double range = 0.5, attackDelay;
    protected boolean isSelected = false, isActive = true;
    protected Point2D position;
    private TargetingType targetingType = TargetingType.FIRST;
    private long lastCall = 0;
    private final TowerStatDisplay statDisplay;
    private final TowerRangeIndicator rangeIndicator;
    private final TowerFunctionsDisplay functionsDisplay;
    private final List<Augment> augments = new ArrayList<>();
    private final List<Invocation> invocations = new ArrayList<>();
    private final Set<SupportTowerBuff> damageBuffs = new HashSet<>();
    private final Set<SupportTowerBuff> atkSpeedBuffs = new HashSet<>();
    private final double maxAugments = 3, maxInvocations = 3;

    public Tower(int points){
        double subDivider = 0.01;
        double pointsSubbed = points / subDivider;

        this.position = new Point2D(0,0);
        this.sizeX = 40;
        this.sizeY = 40;

        double allocPoints;

        damage += (int) ((allocPoints = Main.random.nextDouble() * pointsSubbed) * subDivider * 100);
        damage *= 0.01;
        pointsSubbed -= allocPoints;

        range += (int) ((allocPoints = Main.random.nextDouble() * pointsSubbed) * subDivider * 100);
        range *= 0.01;
        pointsSubbed -= allocPoints;

        atkSpeed += (int) ((allocPoints = Main.random.nextDouble() * pointsSubbed) * subDivider * 100);
        atkSpeed *= 0.01;
        pointsSubbed -= allocPoints;

        this.statDisplay = new TowerStatDisplay(this, new Point2D(Main.canvasSize.getX()*.01, Main.canvasSize.getY()*.3));
        this.rangeIndicator = new TowerRangeIndicator(this,position.add(sizeX * 0.5, sizeY * 0.5));
        this.functionsDisplay = new TowerFunctionsDisplay(this,new Point2D(Main.canvasSize.getX()*.01,statDisplay.getExtremeties().getY()));
        attackDelay = 1_000_000_000 / atkSpeed;
    }
    public Tower(Point2D pos, double dmg, double atkspd, double range, int multishot){
        this(1);
        setPosition(pos);
        setAtkSpeed(atkspd);
        damage = dmg;
        this.range = range;
        this.multishot = multishot;
        rangeIndicator.setDimensions(new Point2D(range,0));
        statDisplay.setText(this.toString());
    }

    public void tick(){
        if(isActive){
            Set<IEnemy> targets = findEnemiesInRange();

            if(lastCall + attackDelay < System.nanoTime() && !targets.isEmpty()){

                List<IEnemy> targetsPrioritized = findTargets(targets);

                for (int i = 0; i < multishot && i < targetsPrioritized.size(); i++) {
                    attack(targetsPrioritized.get(i));
                }

                lastCall = System.nanoTime();
            }

            for(Invocation i : invocations){
                i.evalutate();
            }
        }
    }

    public double getRange(){
        return range * rangeMultiplier;
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.BLUE);
        gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);
    }
    public void setActive(boolean newState){
        this.isActive = newState;
    }
    @Override
    public void applyDamageBuff(SupportTowerBuff buff){
        damageBuffs.add(buff);
    }
    @Override
    public void applyAtkSpeedBuff(SupportTowerBuff buff){
        atkSpeedBuffs.add(buff);
    }
    @Override
    public double getDamage(){return damage;}
    public double getAtkSpeed(){return atkSpeed;}
    public boolean addAugment(Augment augment){
        if(augments.size() < maxAugments){
            if(augment.applyToTower(this)) {
                augments.add(augment);
                return true;
            }else{
                new OnScreenWarning("Augmentation Failed! - Requirements Not Met", Main.canvasSize.multiply(0.4),3).spawn();
            }
        }
        new OnScreenWarning("Augmentation Failed!", Main.canvasSize.multiply(0.4),3).spawn();
        return false;
    }
    public boolean addInvocation(Invocation invocation){
        if(invocations.size() < maxInvocations){
            invocations.add(invocation);
            invocation.setTower(this);
            return true;
        }
        new OnScreenWarning("Invocation Failed!", Main.canvasSize.multiply(0.4),3).spawn();
        return false;
    }
    public List<Augment> getAugments(){return augments;}
    public List<Invocation> getInvocations(){return invocations;}
    public void setAtkSpeed(double newSpeed){
        this.atkSpeed = newSpeed;
        this.attackDelay = 1_000_000_000 / atkSpeed;
    }
    @Override
    public Point2D getPosition() {
        return position;
    }
    @Override
    public void setPosition(Point2D newPos){
        position = newPos;
        rangeIndicator.setPosition(newPos.add(sizeX * 0.5, sizeY * 0.5));
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
    }


    private Set<IEnemy> findEnemiesInRange(){
        Point2D origin = new Point2D(position.getX() + (sizeX * 0.5), position.getY() + (sizeY * 0.5));
        Set<IEnemy> enemiesFound = new HashSet<>();

        for(IEnemy e : IEnemy.active){
            double distX = Math.pow(e.getPosition().getX() - origin.getX(), 2);
            double distY = Math.pow(e.getPosition().getY() - origin.getY(), 2);
            double distance = Math.sqrt(distX + distY);

            if(distance <= getRange()){
                enemiesFound.add(e);
            }

        }

        return enemiesFound;
    }

    private List<IEnemy> findTargets(Set<IEnemy> set){

        List<IEnemy> list = new LinkedList<>(set);

        if(!list.isEmpty()) {

            switch (targetingType) {
                case LAST -> list.sort(Comparator.comparingDouble(IEnemy::getProgress));

                case RANDOM -> list.sort(Comparator.comparingInt(o -> Main.random.nextInt(-1, 1)));

                case FIRST -> list.sort(Comparator.comparingDouble(IEnemy::getProgress).reversed());

                case WEAKEST -> list.sort(Comparator.comparingDouble(IEnemy::getHp));

                case BEEFIEST -> list.sort(Comparator.comparingDouble(IEnemy::getHp).reversed());

                case FASTEST -> list.sort(Comparator.comparingDouble(IEnemy::getMvspeed));
            }
        }

        return list;
    }

    public void setTargetingType(TargetingType type){
        this.targetingType = type;
    }

    private void attack(IEnemy target){
        Bullet b = new AugmentedBullet(position.add(sizeX*0.5,sizeY*0.5),target,this);
        for(Augment a : augments){
            a.applyToBullet(b);
        }
        b.spawn();
    }

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
        rangeIndicator.destroy();
        functionsDisplay.destroy();
        statDisplay.destroy();
    }
    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() > position.getX() && pos.getX() < position.getX() + sizeX) &&
                (pos.getY() > position.getY() && pos.getY() < position.getY() + sizeY);
    }
    @Override
    public String toString(){
        return "DMG: " + damage + "\n" + "RNG: " + range + "\n" + "SPD " + atkSpeed + "\n" + "Target: " + targetingType.asString;
    }

    @Override
    public void onInteraction() {
        if(!isSelected) {

            statDisplay.spawn();
            rangeIndicator.spawn();
            functionsDisplay.spawn();
        }
        isSelected = true;
    }
    @Override
    public void deselect(){
        if(isSelected) {
            statDisplay.setText(this.toString());
            statDisplay.destroy();
            rangeIndicator.destroy();
            functionsDisplay.destroy();
        }
        isSelected = false;
    }

    public void sell() {
        destroy();
        Main.alterGoldAmount(getWorth());
    }

    public double getWorth(){
        return damage + range + atkSpeed;
    }
}
