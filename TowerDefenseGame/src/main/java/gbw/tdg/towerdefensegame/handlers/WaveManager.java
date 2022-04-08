package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.OnScreenWarning;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import javafx.geometry.Point2D;

public class WaveManager implements Tickable {

    //Each wave consists of 3 rounds of X enemies each spawned over 10 seconds.
    //There's a 15 seconds window between each round and a 10 seconds
    //window between each wave.

    private static final int secondsBetweenWaves = 25;
    private long lastCall2;
    private Path path;
    private EnemyBuff currentBuff;
    private int spawnCount;
    private int waveCounter = 0;
    private boolean waitingBetweenWaves;
    private EnemyWave currentWave;

    public WaveManager(Path path){
        this.path = path;
        this.currentBuff = new EnemyBuff(0,0);
        this.currentWave = new EnemyWave(waveCounter,path, this);
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
        currentWave.evaluate();

        if(currentWave.hasNext()){
            Enemy newEnemy = currentWave.getNext();
            newEnemy.applyBuff(currentBuff);
            newEnemy.spawn();
            spawnCount++;

            if(spawnCount % 20 == 0){
                currentBuff.increment(Main.totalGoldEarned / 500.0,Main.totalGoldEarned / 5000.0 );
            }
        }

    }
    public void onNewRoundStart(){

    }
    public void onWaveEnd(){
        waveCounter++;
        currentWave = new EnemyWave(waveCounter, path,this);

    }
    public int getWaveNumber(){return waveCounter +1;}
    public int getRoundNumber(){return currentWave.getRound() +1;}
}
