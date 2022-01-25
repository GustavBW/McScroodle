package gbw.winnions.winnions;

import javafx.geometry.Point2D;

public interface Collidable {

    boolean isInBounds(Point2D posOfCollidingObj);
    void onCollision(Collidable obj);
    double getKnockbackForce();
    Point2D getPosition();
    Point2D[] getVertexes();

}
