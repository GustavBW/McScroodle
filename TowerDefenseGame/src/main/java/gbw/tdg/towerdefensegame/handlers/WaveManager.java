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
    private Path path;
    private EnemyBuff currentBuff;
    private int spawnCount;
    private int waveCounter = 0;
    private boolean hasSetTimeOfWaveEnd,isFirstWave;
    private long timeOfWaveEnd;
    private EnemyWave currentWave;

    public WaveManager(Path path){
        this.path = path;
        this.currentBuff = new EnemyBuff(0,0);
        this.currentWave = new EnemyWave(waveCounter,path, this);
    }

    @Override
    public void spawn() {
        isFirstWave = true;
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

    @Override
    public void tick() {
        if(spawnCount == 0){   //On first wave it needs to wait
            if(!isDoneWaiting()) {
                return;
            }
            onNewWaveStart();
        }

        currentWave.evaluate();

        if(currentWave.hasNext()){
            Enemy newEnemy = currentWave.getNext();
            newEnemy.applyBuff(currentBuff);
            newEnemy.spawn();
            spawnCount++;
        }

    }
    public void onNewRoundStart(){
        new OnScreenWarning("ROUND " + getRoundNumber() + " START",Main.canvasSize.multiply(0.3),3).spawn();
    }
    public void onCurrentRoundEnd(){
        new OnScreenWarning("ROUND " + getRoundNumber() + " WON...kinda",Main.canvasSize.multiply(0.4),3).spawn();
    }
    public void onNewWaveStart(){
        new OnScreenWarning("WAVE " + (waveCounter +1) + " INCOMMING",Main.canvasSize.multiply(0.5),3).spawn();
    }
    public void onWaveEnd(){
        new OnScreenWarning("WAVE " + (waveCounter +1) + " WON", Main.canvasSize.multiply(0.6),3).spawn();
        if(isDoneWaiting()) {
            waveCounter++;
            currentBuff.increment((Main.totalGoldEarned / 500.0) + 1,(Main.totalGoldEarned / 5000.0) + 0.1);
            currentWave = new EnemyWave(waveCounter, path, this);
            onNewWaveStart();
        }
    }
    private boolean isDoneWaiting(){
        if(!hasSetTimeOfWaveEnd) {
            timeOfWaveEnd = System.currentTimeMillis();
            hasSetTimeOfWaveEnd = true;
        }
        boolean done = System.currentTimeMillis() >= timeOfWaveEnd + (secondsBetweenWaves * 1000);
        if(done){
            hasSetTimeOfWaveEnd = false;
        }
        return done;
    }

    public int getWaveNumber(){return waveCounter +1;}
    public int getRoundNumber(){return currentWave.getRound();}
    public void sendNextRound(){
        currentWave.sendNextRoundImmediatly();
        if(hasSetTimeOfWaveEnd){
            timeOfWaveEnd = 0;
        }
    }
}
