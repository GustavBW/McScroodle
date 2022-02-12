package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;

public interface Clickable {

    boolean isInBounds(Point2D pos);
    void onInteraction();
}
