package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.vfx.CircleVFX;
import gbw.tdg.towerdefensegame.UI.vfx.ConnectingLine;
import gbw.tdg.towerdefensegame.UI.vfx.VFX;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BounceBackChainLightning extends Bouncer<Enemy,Bullet> implements Tickable {

    private double searchRange;
    private int maxJumps, jumps, lifetime = 1000;
    private IEnemy currentEnemy;
    private final List<IEnemy> enemiesHit = new ArrayList<>();
    private final Bullet sourceBullet;

    public BounceBackChainLightning(Enemy start, double searchRange, int maxJumps, ChainLightningAugment sourceAug, Bullet sourceBullet){
        super(sourceAug,start,sourceBullet);
        currentEnemy = start;
        this.searchRange = searchRange;
        this.maxJumps = maxJumps;
        this.sourceBullet = sourceBullet;
    }

    @Override
    public void tick() {

        IEnemy enemyStruck = filter(findInRange());

        if(enemyStruck != null){
            enemiesHit.add(enemyStruck);
            IEnemy previousEnemy = currentEnemy;
            currentEnemy = enemyStruck;
            trigger((Enemy) currentEnemy,sourceBullet);
            new ConnectingLine(lifetime, VFX.DEFAULT_PRIO, previousEnemy,currentEnemy)
                    .setStrokeColor(Color.AQUA).spawn();
            jumps++;
        }else{
            destroy();
        }

        if(jumps >= maxJumps){
            destroy();
        }
    }

    private List<IEnemy> findInRange(){
        List<IEnemy> enemiesFound = new ArrayList<>();

        for(IEnemy e : IEnemy.active){
            if(currentEnemy.getPosition().distance(e.getPosition()) <= searchRange){
                enemiesFound.add(e);
            }
        }

        return enemiesFound;
    }

    private IEnemy filter(List<IEnemy> list){
        list.removeAll(enemiesHit);
        return list.isEmpty() ? null : list.get(0);
    }

    public BounceBackChainLightning setLifetime(int ms){
        this.lifetime = ms;
        return this;
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
