package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tower implements Clickable, Tickable, ITower{

    private final double renderingPriority = 55D;
    private double sizeX, sizeY, damage, atkSpeed;
    protected double range, attackDelay;
    protected boolean isSelected = false, isActive = true;
    protected Point2D position;
    private TargetingType targetingType = TargetingType.FIRST;
    private long lastCall = 0;
    private final TowerStatDisplay statDisplay;
    private final TowerRangeIndicator rangeIndicator;
    private final List<Augment> augments = new ArrayList<>();
    private final List<Invocation> invocations = new ArrayList<>();
    private final Set<SupportTowerBuff> damageBuffs = new HashSet<>();
    private final Set<SupportTowerBuff> atkSpeedBuffs = new HashSet<>();
    private final double maxAugments = 3, maxInvocations = 3;

    public Tower(Point2D position, double size, double range, double damage, Main game){
        this.position = position;
        this.sizeX = size;
        this.sizeY = size;
        this.range = range;
        this.damage = damage;
        attackDelay = 1_000_000_000 / atkSpeed;
        this.statDisplay = new TowerStatDisplay(this, position.add(Main.canvasSize.multiply(0.002)));
        this.rangeIndicator = new TowerRangeIndicator(this,position.add(sizeX * 0.5, sizeY * 0.5),range);
    }
    public Tower(int points){
        this.position = new Point2D(0,0);
        this.sizeX = 40;
        this.sizeY = 40;

        while(points > 0) {
            if(Main.random.nextBoolean()) {
                damage++;
                points--;
            }

            if(points <= 0){break;}

            if(Main.random.nextBoolean()) {
                range += 100;
                points--;
            }

            if(points <= 0){break;}

            if(Main.random.nextBoolean()) {
                atkSpeed++;
                points--;
            }
        }
        atkSpeed = Math.max(0.5,atkSpeed);
        range = Math.max(100,range);

        this.statDisplay = new TowerStatDisplay(this, new Point2D(Main.canvasSize.getX()*.01, Main.canvasSize.getY()*.3));
        this.rangeIndicator = new TowerRangeIndicator(this,position.add(sizeX * 0.5, sizeY * 0.5),range);
        attackDelay = 1_000_000_000 / atkSpeed;
    }
    public void tick(){
        if(isActive){
            if(lastCall + attackDelay < System.nanoTime()){
                IEnemy target = findTarget(findEnemies());

                if (target != null) {
                    attack(target);
                }

                lastCall = System.nanoTime();
            }

            for(Invocation i : invocations){
                i.evalutate();
            }
        }
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
    public double getDamage(){return damage;}
    public double getAtkSpeed(){return atkSpeed;}
    public boolean addAugment(Augment augment){
        if(augments.size() < maxAugments){
            augments.add(augment);
            augment.setTower(this);
            return true;
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
    private ArrayList<IEnemy> findEnemies(){
        Point2D origin = new Point2D(position.getX() + (sizeX * 0.5), position.getY() + (sizeY * 0.5));
        ArrayList<IEnemy> enemiesFound = new ArrayList<>();

        for(IEnemy e : IEnemy.active){
            double distX = Math.pow(e.getPosition().getX() - origin.getX(), 2);
            double distY = Math.pow(e.getPosition().getY() - origin.getY(), 2);
            double distance = Math.sqrt(distX + distY);

            if(distance <= range){
                enemiesFound.add(e);
            }

        }

        return enemiesFound;
    }
    private IEnemy findTarget(List<IEnemy> list){

        IEnemy target = null;

        if(!list.isEmpty()) {
            switch (targetingType) {
                case FIRST -> {
                    IEnemy first = list.get(0);

                    for (IEnemy e : list) {
                        if (e.getProgress() > first.getProgress()) {
                            first = e;
                        }
                    }

                    target = first;
                }

                case RANDOM -> {
                    int index = Math.max(Main.random.nextInt(list.size()) - 1,0);

                    target = list.get(index);
                }

                case LAST -> {
                    IEnemy last = list.get(0);

                    for (IEnemy e : list) {
                        if (e.getProgress() < last.getProgress()) {
                            last = e;
                        }
                    }

                    target = last;
                }

                case BEEFIEST -> {

                    IEnemy beefiest = list.get(0);

                    for (IEnemy e : list) {
                        if (e.getHp() > beefiest.getHp()) {
                            beefiest = e;
                        }
                    }

                    target = beefiest;
                }

                case WEAKEST -> {

                    IEnemy weakest = list.get(0);

                    for (IEnemy e : list) {
                        if (e.getHp() < weakest.getHp()) {
                            weakest = e;
                        }
                    }

                    target = weakest;
                }

                case FASTEST -> {

                    IEnemy fastest = list.get(0);

                    for (IEnemy e : list) {
                        if (e.getMvspeed() > fastest.getMvspeed()) {
                            fastest = e;
                        }
                    }

                    target = fastest;

                }
            }
        }

        return target;
    }
    private void attack(IEnemy target){
        Bullet b = new AugmentedBullet(position.add(sizeX*0.5,sizeY*0.5),target,damage,this);
        for(Augment a : augments){
            a.applyTo(b);
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
    }
    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() > position.getX() && pos.getX() < position.getX() + sizeX) &&
                (pos.getY() > position.getY() && pos.getY() < position.getY() + sizeY);
    }
    @Override
    public String toString(){
        return "DMG: " + damage + "\n" + "RNG: " + range + "\n" + "SPD " + atkSpeed;
    }

    @Override
    public void onInteraction() {
        if(!isSelected) {
            statDisplay.spawn();
            rangeIndicator.spawn();
        }
        isSelected = true;
    }
    @Override
    public void deselect(){
        if(isSelected) {
            statDisplay.destroy();
            rangeIndicator.destroy();
        }
        isSelected = false;
    }
}
