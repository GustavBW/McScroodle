package gbw.roguelike.triggers;

import gbw.roguelike.GameObject;
import gbw.roguelike.interfaces.Interactible;
import javafx.geometry.Point2D;

public abstract class Trigger extends GameObject implements Interactible {

    protected double range;
    protected Point2D position;
}
