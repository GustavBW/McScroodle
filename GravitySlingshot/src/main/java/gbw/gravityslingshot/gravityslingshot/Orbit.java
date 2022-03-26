package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

public class Orbit {

    private Point2D center;
    private double radius, ticksPerOrbit, currentTick;


    public Orbit(Point2D center, double radius, double ticksPerOrbit){
        this.center = center;
        this.radius = radius;
        this.ticksPerOrbit = ticksPerOrbit;
    }

    public Point2D getNext(){
        double xVal = Math.sin((currentTick / ticksPerOrbit) * Math.PI * 2);
        double yVal = Math.cos((currentTick / ticksPerOrbit) * Math.PI * 2);
        xVal += center.getX();
        yVal += center.getY();

        currentTick++;
        return new Point2D(xVal,yVal);
    }

}
