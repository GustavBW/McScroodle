package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;

import java.util.ArrayList;
import java.util.List;

public class MassiveInvocation extends Invocation{

    private final List<OrbitingRock> rocks = new ArrayList<>();

    public MassiveInvocation(int level) {
        super(level);
    }



    @Override
    public void attack(List<Enemy> possibleTargets) {}

    @Override
    public void evaluate() {
        Tower tower = getOwner();
        for(OrbitingRock oR : rocks){
            Orbit oROrbit = oR.getOrbit();
            oROrbit.setCenter(tower.getOrigin());
            oROrbit.setRadius(tower.getRange() * .5);
            oROrbit.setOrbitingSpeed((int) (100 / tower.getAtkSpeed()));
        }
    }

    @Override
    public List<Enemy> preattack() {return null;}

    @Override
    public boolean applyToTower(Tower t) {
        rocks.clear();
        double radians = (2 * Math.PI) / getLevel();
        for(int i = 0; i < getLevel(); i++){
            OrbitingRock rock = new OrbitingRock(t.getOrigin(),t.getRange() , t,100, radians * i);
            rock.setDmgMultiplier(getLevel());
            rocks.add(rock);
            rock.spawn();
        }
        t.setRNGInvocation(this);
        return false;
    }

    private double getDamage(){
        return rocks.get(0) == null ? 10 : rocks.get(0).getDamage();
    }

    @Override
    public Invocation copy(int i){
        return new MassiveInvocation(Math.min(i, getMaxLevel()));
    }

    @Override
    public String getDesc(){
        return "The Tower attracts " + getLevel() + " rocks that orbit around it. Each dealing 10 x Tower damage.";
    }

    @Override
    public String getLongDesc(){
        return "The Tower attracts " + getLevel()
                + " rocks that orbit around it at half of Tower range, "
                + "each dealing " + getLevel() + " x Tower damage. The speed of the orbit scales with Tower attack speed."
                + "\n \"It's never too late to get swole.\"";
    }
}
