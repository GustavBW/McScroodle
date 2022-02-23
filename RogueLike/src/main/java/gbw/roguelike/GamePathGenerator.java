package gbw.roguelike;

import javafx.geometry.Point2D;

public class GamePathGenerator {

    public GamePathGenerator(){

    }

    public Room getStartingRoom(){
        return new Room(1, new Point2D(Main.canvasDim.getX() * 0.3,Main.canvasDim.getY() * 0.4));
    }

}
