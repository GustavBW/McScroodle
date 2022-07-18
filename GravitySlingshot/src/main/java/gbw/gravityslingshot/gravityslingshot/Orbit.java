package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Orbit {

    private Point2D center;
    private double radius, orbitingSpeed, currentTick,offset = 0;

    public Orbit(Point2D center, double radius, double ticksPerOrbit){
        this.center = center;
        this.radius = radius;
        this.orbitingSpeed = ticksPerOrbit;
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
    public List<Point2D> getPath(){
        List<Point2D> path = new ArrayList<>((int) orbitingSpeed);
        double tempCurrentTick = currentTick;

        for(int i = 0; i < orbitingSpeed; i++){
            path.add(getNext());
        }

        currentTick = tempCurrentTick;  //Resetting value in case the orbit is in use somewhere else
        return path;
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
