package gbw.roguelike.interfaces;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public interface Clickable {

    public static ArrayList<Clickable> clickables = new ArrayList<>();

    boolean isInBounds(Point2D p);
    void onInteraction();

}
