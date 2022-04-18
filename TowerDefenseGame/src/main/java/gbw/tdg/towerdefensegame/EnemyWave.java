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
    private int currentRound = 1, previousRound = -1;
    private long lastCall;
    private Enemy next;
    private Map<Integer, List<Enemy>> roundMap = new HashMap<>();
    private int enemyCounter;
    private final int waveNumber;
    private final long spawnTimeStamp;
    private long startOfRoundWait;
    private long timeOfRoundEnd;
    private boolean hasSetTimeOfRoundEnd;
    private Path path;
    private WaveManager manager;
    private int waveState;

    public EnemyWave(int waveNumber, Path path, WaveManager manager){
        this.path = path;
        this.manager = manager;
        this.waveNumber = waveNumber;
        this.enemiesEachRound = 10 + waveNumber;
        this.spawnTimeStamp = System.currentTimeMillis();
        roundMap.put(1,calcRoundOne());
        roundMap.put(2,calcRoundTwo());
        roundMap.put(3,calcRoundThree());
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
        next = null;
        switch (waveState){
            case 0,3,6 -> { //Round 1,2,3
                if(isCurrentRoundDone()) {
                    waveState++;
                }
                if(System.currentTimeMillis() > lastCall + (5_000 / enemiesEachRound)) {
                    next = roundMap.get(currentRound).get(enemyCounter % enemiesEachRound);
                }
            }
            case 1,4 -> { //Notify
                manager.onCurrentRoundEnd();
                waveState++;
            }
            case 2,5 -> { //Wait
                if(isDoneWaiting()){
                    currentRound++;
                    waveState++;
                    manager.onNewRoundStart();
                }
            }
            case 7 -> manager.onWaveEnd();
        }
    }
    private boolean isCurrentRoundDone(){
        return enemyCounter % enemiesEachRound == 0 && enemyCounter - ((currentRound - 1) * enemiesEachRound) > 0;
    }
    private boolean isDoneWaiting(){
        if(!hasSetTimeOfRoundEnd) {
            startOfRoundWait = System.currentTimeMillis();
            hasSetTimeOfRoundEnd = true;
        }
        boolean done = System.currentTimeMillis() >= startOfRoundWait + (secondsBetweenRounds * 1000);
        if(done){
            hasSetTimeOfRoundEnd = false;
        }
        return done;
    }
    public void sendNextRoundImmediatly(){
        startOfRoundWait = 0;
    }
    public int getWaveState(){
        return waveState;
    }
}
