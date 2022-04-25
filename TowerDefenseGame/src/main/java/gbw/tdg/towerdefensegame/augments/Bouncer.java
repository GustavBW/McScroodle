package gbw.tdg.towerdefensegame.augments;

import gbw.tdg.towerdefensegame.Tickable;

public abstract class Bouncer<T,R> implements Tickable {

    protected BounceReciever<T,R> receiver;
    protected T returningObj1;
    protected R returningObj2;

    public Bouncer(BounceReciever<T,R> receiver){
        this(receiver,null,null);
    }

    public Bouncer(BounceReciever<T,R> receiver, T t, R r){
        this.receiver = receiver;
        returningObj1 = t;
        returningObj2 = r;
    }

    public void trigger(){
        receiver.bounce(returningObj1, returningObj2);
    }
    public void trigger(T t, R r){
        receiver.bounce(t,r);
    }


}
