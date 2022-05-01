package gbw.tdg.towerdefensegame;

public abstract class TimedEventBuffer<T,R> extends EventBuffer<T,R> implements Tickable {

    //Auto-Triggers evaluate and execute events based on a time interval.
    //Execute wont trigger if the accumulated object(s) are null.

    private int bufferLengthMillis;
    private long accTimeStamp;

    public TimedEventBuffer(int bufferLengthMillis){
        super();
        this.bufferLengthMillis = bufferLengthMillis;
        spawn();
    }

    @Override
    public void tick(){
        if(System.currentTimeMillis() >= bufferLengthMillis + accTimeStamp){
            accTimeStamp = System.currentTimeMillis();
            
            if(buffer.isEmpty()){
                return;
            }

            R nullCheck = evaluate(buffer);

            if(nullCheck != null){
                execute(nullCheck);
                buffer.clear();
            }
        }
    }

    public void setBufferLength(int millis){
        this.bufferLengthMillis = millis;
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

}
