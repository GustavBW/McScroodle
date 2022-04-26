package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;

import java.util.*;

public class BasicSPDInvocation extends Invocation {

    private double searchRange;
    private boolean useTowerRange = true;

    public BasicSPDInvocation(int level){
        super(level);
    }

    protected List<Enemy> sortTargets(Set<Enemy> set){

        List<Enemy> list = new LinkedList<>(set);
        if(getOwner() == null){return list;}

        if(!list.isEmpty()) {

            switch (getOwner().getTargetingType()) {
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

    protected Set<Enemy> findEnemiesInRange(){

        Set<Enemy> enemiesFound = new HashSet<>();
        if(getOwner() == null){return enemiesFound;}

        Point2D position = getOwner().getPosition();
        double tRangeSQ = useTowerRange ? getOwner().getRange() * getOwner().getRange() : searchRange * searchRange;
        Point2D dim = getOwner().getDimensions();
        Point2D origin = new Point2D(position.getX() + (dim.getX() * 0.5), position.getY() + (dim.getY() * 0.5));

        for(IEnemy e : IEnemy.active){
            double distX = Math.pow(e.getPosition().getX() - origin.getX(), 2);
            double distY = Math.pow(e.getPosition().getY() - origin.getY(), 2);
            double distanceSQ = distX + distY;

            if(distanceSQ <= tRangeSQ){
                enemiesFound.add((Enemy) e);
            }

        }

        return enemiesFound;
    }

    @Override
    public Invocation copy(){
        return new BasicSPDInvocation(getLevel());
    }

    @Override
    public void attack(List<Enemy> possibleTargets) {}

    @Override
    public void evaluate() {}

    @Override
    public List<Enemy> preattack() {
        return sortTargets(findEnemiesInRange());
    }
    public void setUseTowerRange(boolean state){
        useTowerRange = state;
    }
    public void setSearchRange(double range){
        this.searchRange = range;
    }

    @Override
    public boolean applyToTower(Tower t) {
        t.setSPDInvocation(this);
        return true;
    }

    @Override
    public String getDesc(){
        return "As basic as basic comes.";
    }

}
