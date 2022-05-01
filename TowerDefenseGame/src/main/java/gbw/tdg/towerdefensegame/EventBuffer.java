package gbw.tdg.towerdefensegame;

import java.util.*;

public abstract class EventBuffer<T,R> {

    //Is used to collect many objects, then turn them into one single object
    //and execute evaluations on that calling the Trigger() method.

    protected Deque<T> buffer;

    public EventBuffer(){
        buffer = new ArrayDeque<>();
    }

    public List<T> getBuffer(){
        return new ArrayList<>(buffer);
    }

    public abstract R evaluate(Deque<T> buffer);

    public abstract void execute(R accumulated);

    public void add(T obj){
        buffer.add(obj);
    }

    public void trigger(){
        execute(evaluate(buffer));
        buffer.clear();
    }

}
