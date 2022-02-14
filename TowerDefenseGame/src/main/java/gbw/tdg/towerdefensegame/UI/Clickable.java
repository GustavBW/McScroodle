package gbw.tdg.towerdefensegame.UI;

import javafx.geometry.Point2D;

public interface Clickable {

    boolean isInBounds(Point2D pos);
    void onInteraction();
}
