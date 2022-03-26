package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GravityObject implements Tickable, Renderable, StaticMass {

    private final double mass;
    private Point2D position;
    private Orbit orbit;


    public GravityObject(double mass, Point2D position) {
        this.mass = mass;
        this.position = position;
        orbit = new Orbit(position,0,0);
    }
    public GravityObject(double mass, Point2D position, Orbit orbit){
        this(mass, position);
        this.orbit = orbit;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(position.getX(),position.getY(), mass, mass, mass, mass);
    }

    @Override
    public void tick() {
        position = orbit.getNext();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
        Renderable.expended.add(this);
        StaticMass.expended.add(this);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
        StaticMass.newborn.add(this);
    }

    @Override
    public double getMass() {
        return 0;
    }
}
