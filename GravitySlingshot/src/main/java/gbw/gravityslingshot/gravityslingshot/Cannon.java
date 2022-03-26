package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Cannon implements Tickable, Renderable {

    private double angle;
    private final Point2D position;

    public Cannon(double angle, Point2D position) {
        this.angle = angle;
        this.position = position;
    }


    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void tick() {

    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
        Renderable.expended.add(this);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
    }
}
