package gbw.tdg.towerdefensegame.augments;

@FunctionalInterface
public interface BounceReciever<T,R> {

    void bounce(T t, R r);

}
