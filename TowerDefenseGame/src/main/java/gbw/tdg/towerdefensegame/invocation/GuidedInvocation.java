package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.BounceBackBullet;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;

import java.util.List;

public class GuidedInvocation extends BasicDMGInvocation{

    public GuidedInvocation(int level) {
        super(level);
    }

    @Override
    public void attack(List<Enemy> targets){
        if(getOwner() == null){return;}

        Point2D position = getOwner().getOrigin();

        for(Enemy target : targets) {
            //applyAugs(new BounceBackBullet(position, target, getOwner(),this).setDamagePercent(1 + stacks / 10D)).spawn();
        }
    }

    @Override
    public boolean applyToTower(Tower t){
        return super.applyToTower(t);
    }

    @Override
    public Invocation copy(){
        return new GuidedInvocation(getLevel());
    }

    public String getName(){
        return "Guided";
    }

    public String getDesc(){
        return "Bullets will be fired towards the mouse";
    }
    public String getLongDesc(){
        return "Hitting enemies adding stacks. These stay alive";
    }

}
