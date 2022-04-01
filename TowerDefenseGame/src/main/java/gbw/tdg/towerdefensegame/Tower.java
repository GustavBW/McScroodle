package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.TowerStatDisplay;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Tower implements Clickable, Tickable, ITower{

    private static final double renderingPriority = 55D;
    private double size, range, damage, atkSpeed, attackDelay;
    private Main game;
    private boolean isSelected = false, isActive = true;
    private Point2D position;
    private TargetingType targetingType = TargetingType.FIRST;
    private long lastCall = 0;
    private final TowerStatDisplay statDisplay;

    private static Color rangeIndicatorColor = new Color(0,0,0,0.28);

    public Tower(Point2D position, double size, double range, double damage, Main game){
        this.position = position;
        this.size = size;
        this.range = range;
        this.damage = damage;
        this.game = game;
        attackDelay = 1_000_000_000 / atkSpeed;
        this.statDisplay = new TowerStatDisplay(this, position.add(Main.canvasSize.multiply(0.002)));
    }
    public Tower(int points){
        this.position = new Point2D(0,0);
        this.size = 40;

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

        this.statDisplay = new TowerStatDisplay(this, position.add(Main.canvasSize.multiply(0.002)));
        this.game = Main.getInstance();
        attackDelay = 1_000_000_000 / atkSpeed;
        System.out.println("Tower made with " + "DMG: " + damage + " RNG: " + range + " SPD: " + atkSpeed + " Points remaining: " + points);
    }

    public void tick(){
        if(isActive && lastCall + attackDelay < System.nanoTime()){

            IEnemy target = findTarget(findEnemies());

            if(target != null){
                attack(target);
            }

            lastCall = System.nanoTime();
        }
    }

    public void render(GraphicsContext gc){

        gc.setFill(Color.BLUE);
        gc.fillRect(position.getX() - size / 2, position.getY() - size / 2, size, size);

        if(isSelected) {
            gc.setFill(rangeIndicatorColor);
            gc.fillRoundRect(position.getX() - range, position.getY() - range, range * 2, range * 2, range * 2, range* 2);
            statDisplay.render(gc);
        }
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D newPos){
        position = newPos;
        statDisplay.setPosition(position.add(Main.canvasSize.multiply(0.002)));
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }


    private ArrayList<IEnemy> findEnemies(){
        Point2D origin = new Point2D(position.getX() - size / 2, position.getY() - size /2);
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
        shootAt(target);
    }

    private void shootAt(IEnemy target){
        Point2D dirToTarget = target.getPosition().subtract(position);
        dirToTarget = dirToTarget.normalize();

        DummyBullet d = new DummyBullet(position,dirToTarget,target,damage);
        d.spawn();
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
        return (pos.getX() > position.getX() && pos.getX() < position.getX() + size) &&
                (pos.getY() > position.getY() && pos.getY() < position.getY() + size);
    }

    @Override
    public String toString(){
        return "DMG: " + damage + "\n" + "RNG: " + range + "\n" + "SPD " + atkSpeed;
    }

    @Override
    public void onInteraction() {
        isSelected = true;
        System.out.println("You clicked " + this);
    }
    @Override
    public void deselect(){
        isSelected = false;
    }
}
