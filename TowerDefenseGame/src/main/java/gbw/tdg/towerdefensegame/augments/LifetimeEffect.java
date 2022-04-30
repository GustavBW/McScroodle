package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.enemies.Enemy;

public class LifetimeEffect{

    protected long lifetimeMS,spawnTime,lastCall,timeRemaining;
    protected Object owner;

    public LifetimeEffect(Object owner){
        this.owner = owner;
    }
    public LifetimeEffect(Object owner, long lifetimeMS){
        this(owner);
        this.lifetimeMS = lifetimeMS;
        this.timeRemaining = lifetimeMS;
    }

    protected void evalLifetime(Enemy toWhomAmIApplied){
        timeRemaining = (spawnTime + lifetimeMS) - System.currentTimeMillis();
        if(timeRemaining <= 0) {
            toWhomAmIApplied.removeLifetimeEffect(this);
        }
    }

    public synchronized void evaluateOn(Enemy e){
        evalLifetime(e);
    }

    public String getEffectString(){return "unknown";}

    public Object getOwner(){
        return owner;
    }

    public long getLifetimeMS(){
        return lifetimeMS;
    }

    public void setSpawnTime(long time){
        this.spawnTime = time;
    }

    @Override
    public String toString(){
        String superStr = super.toString();
        int index = superStr.lastIndexOf('.') + 1;
        String ownerStr = this.owner.toString();
        int index2 = ownerStr.lastIndexOf('.') + 1;
        return superStr.substring(index) + " owner: " + ownerStr.substring(index2);
    }

    public void resetSpawntime(){
        this.spawnTime = System.currentTimeMillis();
    }
}
