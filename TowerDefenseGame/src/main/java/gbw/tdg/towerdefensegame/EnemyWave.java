package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.handlers.WaveManager;

import java.util.*;

public class EnemyWave {

    //Each wave consists of 3 rounds of X enemies each spawned over 10 seconds.
    //There's a 15 seconds window between each round and a 10 seconds
    //window between each wave.

    private final int secondsBetweenRounds = 15;
    private int enemiesEachRound = 10;
    private boolean waitingBetweenRounds = false;
    private int currentRound, previousRound = -1;
    private long lastCall;
    private Enemy next;
    private Map<Integer, List<Enemy>> roundMap = new HashMap<>();
    private int enemyCounter;
    private final int waveNumber;
    private final long spawnTimeStamp;
    private long timeOfRoundEnd;
    private Path path;
    private WaveManager manager;

    public EnemyWave(int waveNumber, Path path, WaveManager manager){
        this.path = path;
        this.manager = manager;
        this.waveNumber = waveNumber;
        this.enemiesEachRound = 10 + waveNumber;
        this.spawnTimeStamp = System.currentTimeMillis();
        roundMap.put(0,calcRoundOne());
        roundMap.put(1,calcRoundTwo());
        roundMap.put(2,calcRoundThree());
    }

    private List<Enemy> calcRoundThree() {
        List<Enemy> roundThree = new ArrayList<>();
        for(int i = 0; i < enemiesEachRound; i++){
            roundThree.add(new Enemy(path.getStartAsPoint2D(), path));
        }
        return roundThree;
    }

    private List<Enemy> calcRoundTwo() {
        List<Enemy> roundTwo = new ArrayList<>();
        for(int i = 0; i < enemiesEachRound; i++){
            roundTwo.add(new Enemy(path.getStartAsPoint2D(), path));
        }
        return roundTwo;
    }

    private List<Enemy> calcRoundOne() {
        List<Enemy> roundOne = new ArrayList<>();
        for(int i = 0; i < enemiesEachRound; i++){
            roundOne.add(new Enemy(path.getStartAsPoint2D(), path));
        }
        return roundOne;
    }

    public Enemy getNext(){
        enemyCounter++;
        Enemy temp = next.clone();
        lastCall = System.currentTimeMillis();
        return temp;
    }

    public boolean hasNext(){
        return next != null;
    }

    public int getRound(){
        return currentRound;
    }
    public int getWaveNumber(){
        return waveNumber;
    }

    public void evaluate(){
        currentRound = (enemyCounter / enemiesEachRound);
        if(previousRound != currentRound){
            manager.onNewRoundStart();
            previousRound = currentRound;
        }

        if(enemyCounter % enemiesEachRound == 0) {
            timeOfRoundEnd = waitingBetweenRounds ? timeOfRoundEnd : System.currentTimeMillis();
            waitingBetweenRounds = timeOfRoundEnd + (secondsBetweenRounds * 1_000) > System.currentTimeMillis();
        }

        if(!waitingBetweenRounds && System.currentTimeMillis() > lastCall + (10_000 / enemiesEachRound)) {
            if(enemyCounter >= enemiesEachRound * 3){
                manager.onWaveEnd();
            }else {
                next = roundMap.get(currentRound).get(enemyCounter - (currentRound * enemiesEachRound));
            }
        }else{
            next = null;
        }
    }
}
