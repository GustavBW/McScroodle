package gbw.roguelike.interfaces;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public interface Interactible {

    ArrayList<Interactible> interactibles = new ArrayList<>();

    boolean isInBounds(Point2D p);
    void onInteraction();
}
