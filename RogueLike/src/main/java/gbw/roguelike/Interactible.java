package gbw.roguelike;

import javafx.geometry.Point2D;

public interface Interactible {

    boolean isInBounds(Point2D p);
    void onInteraction();
}
