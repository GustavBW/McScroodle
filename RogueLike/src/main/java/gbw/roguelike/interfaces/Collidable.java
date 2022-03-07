package gbw.roguelike.interfaces;

import gbw.roguelike.Vector2D;

import java.util.ArrayList;

public interface Collidable {

    ArrayList<Collidable> collidables = new ArrayList<>();

    boolean intersects(Vector2D pos, Vector2D vec);
    boolean onIntersection();
    Vector2D getVelocity();
    Vector2D getPosition();
    boolean isDynamic();
    double getMass();

}
