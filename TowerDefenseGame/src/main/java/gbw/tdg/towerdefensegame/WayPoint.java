package gbw.tdg.towerdefensegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WayPoint {

    public double x,y;
    private int id;

    public WayPoint(double x, double y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(x - 5,y - 5,10,10,10,10);
    }

    @Override
    public String toString(){
        return "WayPoint " + id + " | " + x + " " + y;
    }



    public int getId(){return id;}
}
