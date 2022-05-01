package gbw.tdg.towerdefensegame;

import java.util.Deque;

public abstract class SizedEventBuffer<T,R> extends EventBuffer<T,R>{

    //Evaluates and execute buffer when an "add" event would bring the amount of
    //objects in the buffer above the set threshold

    private int threshold,bufferSize;

    public SizedEventBuffer(int threshold){
        if(threshold < 1){
            throw new IllegalArgumentException("Invalid threshold size");
        }
        this.threshold = threshold;
    }

    @Override
    public void add(T obj){
        if(bufferSize + 1 >= threshold){
            execute(evaluate(buffer));
            buffer.clear();
            bufferSize = 0;
        }
        buffer.add(obj);
        bufferSize++;
    }

    public void setThreshold(int a){
        this.threshold = a;
    }

    @Override
    public void trigger(){
        super.trigger();
        bufferSize = 0;
    }

    public abstract R evaluate(Deque<T> buffer);
    public abstract void execute(R accumulated);
}
