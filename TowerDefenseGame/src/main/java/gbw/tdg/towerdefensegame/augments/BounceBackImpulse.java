package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Tickable;


public class BounceBackImpulse<T,R> extends Bouncer<T,R> {

    private int delayMS;
    private long spawnTime = Long.MAX_VALUE;

    public BounceBackImpulse(int delayMS, BounceReciever<T,R> receiver, T t, R r){
        super(receiver);
        this.delayMS = delayMS;
        returningObj1 = t;
        returningObj2 = r;
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() >= spawnTime + delayMS){
            trigger();
            destroy();
        }
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        spawnTime = System.currentTimeMillis();
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

}
