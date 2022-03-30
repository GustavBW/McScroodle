package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Tower implements Clickable, Tickable, ITower{

    private static final double renderingPriority = 55D;
    private double size, range, damage, atkSpeed = 40, attackDelay;
    private Main game;
    private boolean isSelected = true, isActive = true;
    private Point2D position;
    private TargetingType targetingType = TargetingType.FIRST;
    private long lastCall = 0;
    private Random random;

    private static Color rangeIndicatorColor = new Color(0,0,0,0.5);

    public Tower(Point2D position, double size, double range, double damage, Main game){
        this.position = position;
        this.size = size;
        this.range = range;
        this.damage = damage;
        this.game = game;
        random = new Random();
        attackDelay = 1_000_000_000 / atkSpeed;
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

        if(isSelected) {
            gc.setFill(rangeIndicatorColor);
            gc.fillRoundRect(position.getX() - range, position.getY() - range, range * 2, range * 2, range * 2, range* 2);
        }

        gc.setFill(Color.BLUE);
        gc.fillRect(position.getX() - size / 2, position.getY() - size / 2, size, size);
    }

    @Override
    public Point2D getPosition() {
        return position;
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
                    int index = Math.max(random.nextInt(list.size()) - 1,0);

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
        return false;
    }

    @Override
    public void onInteraction() {
        isSelected = !isSelected;
    }
}
