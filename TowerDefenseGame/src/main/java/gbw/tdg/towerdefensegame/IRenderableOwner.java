package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;

public interface IRenderableOwner extends IGameObject {

    Point2D getPosition();
    double getRenderingPriority();

}
