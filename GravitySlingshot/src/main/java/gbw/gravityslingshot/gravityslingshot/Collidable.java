package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public interface Collidable extends IGameObject{

    List<Collidable> active = new ArrayList<>();
    List<Collidable> expended = new ArrayList<>();
    List<Collidable> newborn = new ArrayList<>();

    boolean isInBounds(Point2D p);
    void onCollisionWith(Collidable c);

}
