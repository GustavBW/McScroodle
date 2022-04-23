package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.vfx.CircleVFX;
import gbw.tdg.towerdefensegame.UI.vfx.ConnectingLine;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public class BounceBackLightning implements Tickable {

    private double searchRange,rendPrio;
    private int maxJumps, jumpDelayMS = 100, jumps;
    private long lastCall;
    private IEnemy currentEnemy, previousEnemy;
    private final Set<IEnemy> enemiesHit = new HashSet<>();
    private final LightningAugment sourceAug;
    private final Bullet sourceBullet;

    public BounceBackLightning(Enemy start, double searchRange, int maxJumps, LightningAugment sourceAug, Bullet sourceBullet){
        this.rendPrio = 70;
        currentEnemy = start;
        this.searchRange = searchRange;
        this.maxJumps = maxJumps;
        this.sourceAug = sourceAug;
        this.sourceBullet = sourceBullet;
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() >= lastCall + jumpDelayMS){
            IEnemy enemyStruck = filter(findInRange());

            if(enemyStruck != null){
                enemiesHit.add(enemyStruck);
                previousEnemy = currentEnemy;
                currentEnemy = enemyStruck;
                sourceAug.onEnemyHitByLightning((Enemy) currentEnemy,sourceBullet);
                new ConnectingLine(1_000,70,previousEnemy,currentEnemy).spawn();
                new CircleVFX(3000,100,currentEnemy.getPosition(),searchRange);
                jumps++;
            }else{
                destroy();
            }

            lastCall = System.currentTimeMillis();
        }

        if(jumps >= maxJumps){
            destroy();
        }
    }

    private Set<IEnemy> findInRange(){
        Set<IEnemy> enemiesFound = new HashSet<>();

        for(IEnemy e : IEnemy.active){
            if(currentEnemy.getPosition().distance(e.getPosition()) <= searchRange){
                enemiesFound.add(e);
            }
        }
        return enemiesFound;
    }
    private IEnemy filter(Set<IEnemy> set){
        for(IEnemy e : set){
            if(!enemiesHit.contains(e)){
                return e;
            }
        }
        return null;
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
