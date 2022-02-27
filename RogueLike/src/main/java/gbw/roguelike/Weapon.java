package gbw.roguelike;

import javafx.geometry.Point2D;

public class Weapon extends Item {

    protected String name;
    protected String displayName;

    public Weapon(Point2D position, String name, int id) {
        super(position, name, id);

    }

    public String getName(){
        return name;
    }

}
