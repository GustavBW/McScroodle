package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IGameObject;
import gbw.tdg.towerdefensegame.IProjectile;
import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public interface Collidable extends IGameObject {

    Set<IProjectile> active = new HashSet<>();
    Set<IProjectile> expended = new HashSet<>();
    Set<IProjectile> newborn = new HashSet<>();

    boolean isInBounds(Collidable c);
    void onCollision(Collidable c);
    Point2D getPosition();

}
