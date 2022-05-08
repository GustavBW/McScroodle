package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IGameObject;
import javafx.scene.paint.Color;

public class Message implements IGameObject {

    public final String text;
    private int durMS;
    private long timeout;
    private Color color = Color.WHITE;

    public Message(String msg, int durMS){
        this.text = msg;
        this.durMS = durMS;
    }
    public Message(String msg, int durMS, Color color){
        this(msg,durMS);
        this.color = color;
    }
    public void reset(){
        this.timeout = durMS + System.currentTimeMillis();
    }
    public void setTimeout(long timestamp){
        this.timeout = timestamp;
    }
    public int getDurMS(){
        return durMS;
    }
    public boolean timeout(){
        return System.currentTimeMillis() >= timeout;
    }
    public Color getColor(){
        return color;
    }
    public void setColor(Color c2){
        this.color = c2;
    }

    @Override
    public void spawn() {

    }

    @Override
    public void destroy() {

    }
}
