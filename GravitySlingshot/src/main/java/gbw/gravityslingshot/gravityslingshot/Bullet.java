package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class Bullet implements Tickable, Renderable, MassEffected, Collidable {

    private Point2D position;
    private Point2D velocity;
    private double mass;
    private double size;
    private double speed;

    public Bullet(Point2D pos, Point2D vel){
        this(pos, vel, 10);
    }
    public Bullet(Point2D pos, Point2D vel, double mass){
        this.position = pos;
        this.velocity = vel;
        this.mass = mass;
        this.size = mass / 4.0;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillRoundRect(position.getX(),position.getY(), mass, mass, mass, mass);
    }

    @Override
    public void tick() {
        position = position.add(velocity.multiply(speed));
    }

    @Override
    public boolean isInBounds(Point2D p) {
        double distance = position.distance(p);
        return distance < size;
    }

    @Override
    public void onCollisionWith(Collidable c) {
        if(c instanceof GravityObject){
            destroy();
        }
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void destroy() {
        MassEffected.expended.add(this);
        Tickable.expended.add(this);
        Renderable.expended.add(this);
    }

    @Override
    public void spawn() {
        MassEffected.newborn.add(this);
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
    }

    @Override
    public void evaluateGravity(double force, Point2D direction) {
        Point2D change = direction.multiply(force);
        position = position.add(change);
    }

    @Override
    public double getMass() {
        return mass;
    }
    public void setMass(double i){mass += i;}

    @Override
    public String toString(){
        return "Bullet at " + position.getX() + " " + position.getY();
    }
}
