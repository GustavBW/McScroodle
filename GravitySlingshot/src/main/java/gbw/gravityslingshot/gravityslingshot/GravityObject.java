package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

public class GravityObject {

    private final double mass;
    private final Point2D position;

    public GravityObject(double mass, Point2D position) {
        this.mass = mass;
        this.position = position;
    }
}
