package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

public interface IGameObject {

    Point2D getPosition();
    void destroy();
    void spawn();
}
