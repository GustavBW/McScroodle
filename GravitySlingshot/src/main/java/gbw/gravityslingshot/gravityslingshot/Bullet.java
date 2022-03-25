package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;

public class Bullet implements Tickable, Renderable, MassEffected {

    private Point2D position;
    private Point2D velocity;
    private double mass;

    public Bullet(Point2D pos, Point2D vel){
        this(pos, vel, 10);
    }
    public Bullet(Point2D pos, Point2D vel, double mass){
        this.position = pos;
        this.velocity = vel;
        this.mass = mass;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillRoundRect(position.getX(),position.getY(), mass, mass, mass, mass);
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

    }

    @Override
    public void evaluate(MassEffected m) {

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
