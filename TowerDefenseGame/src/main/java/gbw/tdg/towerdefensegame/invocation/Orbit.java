package gbw.tdg.towerdefensegame.invocation;

import javafx.geometry.Point2D;

public class Orbit {
    //Pulled and modified from own prior project "GravitySlingshot"

    private Point2D center;
    private double radius, orbitingSpeed, currentTick,offset = 0;

    public Orbit(Point2D center, double radius, double orbitingSpeed){
        this.center = center;
        this.radius = radius;
        this.orbitingSpeed = orbitingSpeed;
    }
    public Orbit(Point2D center, double radius, double orbitingSpeed,double offsetRadians){
        this(center,radius,orbitingSpeed);
        this.offset = offsetRadians;
    }

    public Point2D getNext(){
        double xVal = Math.sin((currentTick / orbitingSpeed) * Math.PI * 2 + offset) * radius;
        double yVal = Math.cos((currentTick / orbitingSpeed) * Math.PI * 2 + offset) * radius;
        xVal += center.getX();
        yVal += center.getY();

        currentTick++;
        return new Point2D(xVal,yVal);
    }

    public void setCenter(Point2D pos){
        this.center = pos;
    }
    public void setRadius(double radius){
        this.radius = radius;
    }
    public void setOrbitingSpeed(int speed){
        this.orbitingSpeed = speed;
    }

}
