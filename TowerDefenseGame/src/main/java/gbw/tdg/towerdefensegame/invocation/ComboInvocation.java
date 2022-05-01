package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.BounceBackBullet;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.augments.BounceReciever;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;

import java.util.List;

public class ComboInvocation extends BasicDMGInvocation implements BounceReciever<Enemy, Bullet>, Tickable {

    private int maxTimeLeft = 3_000, stacks, boost = 500, gracePeriod = 100;
    private long timeLeft, lastCall;

    public ComboInvocation(int level) {
        super(level);
    }

    @Override
    public void attack(List<Enemy> targets){
        if(getOwner() == null){return;}

        Point2D position = getOwner().getOrigin();

        for(Enemy target : targets) {
            applyAugs(new BounceBackBullet(position, target, getOwner(),this).setDamagePercent(1 + stacks / 10D)).spawn();
        }
    }

    @Override
    public void tick() {
        long diff = System.currentTimeMillis() - lastCall;
        lastCall = System.currentTimeMillis();
        timeLeft -= diff;
        if(timeLeft <= 0){
            stacks = 0;
            timeLeft = 0;
        }
    }

    private void extendTimeout() {
        if(timeLeft + boost > maxTimeLeft){
            timeLeft = maxTimeLeft;
        }else {
            timeLeft += boost;
        }
    }

    @Override
    public void bounce(Enemy enemy, Bullet bullet) {
        extendTimeout();
        stacks++;
    }

    @Override
    public boolean applyToTower(Tower t){
        spawn();
        boost = (int) Math.min(boost,t.getAttackDelayMS()) + gracePeriod;
        return super.applyToTower(t);
    }

    public String getName(){
        return "Combo";
    }

    public String getDesc(){
        return "Hitting enemies consecutively adds stacks that run out. Each stack increase bullet damage by 10%";
    }
    public String getLongDesc(){
        return "Hitting enemies adding stacks. These stay alive";
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }
}
