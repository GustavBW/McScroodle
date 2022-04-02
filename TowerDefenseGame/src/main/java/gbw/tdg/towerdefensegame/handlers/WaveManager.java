package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.*;

public class WaveManager implements Tickable {


    private long lastCall2;
    private Path path;
    private EnemyBuff currentBuff;
    private int spawnCount;

    public WaveManager(Path path){
        this.path = path;
        this.currentBuff = new EnemyBuff(0,0);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

    @Override
    public void tick() {
        if(lastCall2 + 500 < System.currentTimeMillis()){
            Enemy newEnemy = new Enemy(path.getStartPoint().x,path.getStartPoint().y,path);
            newEnemy.applyBuff(currentBuff);
            newEnemy.spawn();
            lastCall2 = System.currentTimeMillis();
            spawnCount++;

            if(spawnCount % 20 == 0){
                currentBuff.increment(Main.totalGoldEarned / 100.0,Main.totalGoldEarned / 1000.0 );
            }
        }


    }
}
