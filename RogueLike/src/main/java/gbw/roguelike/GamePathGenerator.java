package gbw.roguelike;

import javafx.geometry.Point2D;

public class GamePathGenerator {

    public GamePathGenerator(){

    }

    public Room getStartingRoom(){
        return new Room(1, new Point2D(0,0));
    }

}
