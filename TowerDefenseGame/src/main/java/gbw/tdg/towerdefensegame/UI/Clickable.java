package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IGameObject;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public interface Clickable extends IGameObject {

    Set<Clickable> active = new HashSet<>();
    Set<Clickable> expended = new HashSet<>();
    Set<Clickable> newborn = new HashSet<>();

    boolean isInBounds(Point2D pos);
    void onInteraction();
    void deselect();
}
