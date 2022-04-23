package gbw.tdg.towerdefensegame.enemies;

public class LifetimeEffect implements Comparable<LifetimeEffect>{

    protected long lifetimeMS,spawnTime,lastCall;
    protected Object owner;

    public LifetimeEffect(Object owner){
        this.owner = owner;
    }

    protected void evalLifetime(Enemy toWhomAmIApplied){
        if(System.currentTimeMillis() >= spawnTime + lifetimeMS) {
            toWhomAmIApplied.removeLifetimeEffect(this);
        }
    }

    public void evaluateOn(Enemy e){}

    public String getEffectString(){return "unknown";}

    @Override
    public int compareTo(LifetimeEffect o) {
        return this.owner.equals(o.owner) ? 0 : -1;
    }
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
