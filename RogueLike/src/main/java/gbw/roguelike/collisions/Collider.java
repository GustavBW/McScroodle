package gbw.roguelike.collisions;

import gbw.roguelike.Vector2D;
import gbw.roguelike.interfaces.Collidable;

public class Collider implements Collidable {

    protected boolean isDynamic;
    protected double mass;
    private Vector2D latestInputPos;
    private Vector2D latestInputVec;

    @Override
    public boolean intersects(Vector2D pos, Vector2D vec) {
        latestInputPos = pos;
        latestInputVec = vec;
        return false;
    }

    @Override
    public boolean onIntersection() {
        return false;
    }

    @Override
    public Vector2D getVelocity() {
        return null;
    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    @Override
    public boolean isDynamic() {
        return isDynamic;
    }

    @Override
    public double getMass() {
        return mass;
    }
}
