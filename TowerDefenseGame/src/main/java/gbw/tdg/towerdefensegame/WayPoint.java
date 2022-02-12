package gbw.tdg.towerdefensegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WayPoint {

    public double x,y;

    public WayPoint(double x, double y){
        this.x = x;
        this.y = y;
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(x - 5,y - 5,10,10,10,10);
    }

}
