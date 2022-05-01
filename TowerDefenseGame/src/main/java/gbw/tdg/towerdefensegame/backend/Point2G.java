package gbw.tdg.towerdefensegame.backend;

import gbw.tdg.towerdefensegame.Main;
import javafx.geometry.Point2D;

public abstract class Point2G {

    public static final Point2D ONE = new Point2D(1, 1);
    public static final Point2D UP = new Point2D(0,-1);
    public static final Point2D DOWN = new Point2D(0,1);
    public static final Point2D LEFT = new Point2D(-1,0);
    public static final Point2D RIGHT = new Point2D(1,0);
    public static final Point2D DOWN_RIGHT = new Point2D(1,1);
    public static final Point2D UP_10 = UP.multiply(10);
    public static final Point2D DOWN_10 = DOWN.multiply(10);
    public static final Point2D LEFT_10 = LEFT.multiply(10);
    public static final Point2D RIGHT_10 = RIGHT.multiply(10);
    public static final Point2D UP_100 = UP.multiply(100);
    public static final Point2D DOWN_100 = DOWN.multiply(100);
    public static final Point2D LEFT_100 = LEFT.multiply(100);
    public static final Point2D RIGHT_100 = RIGHT.multiply(100);

    public static Point2D getRandomlyScewedVector(Point2D sourceVec, double scew){
        //Scew is in "X" amount of 90 degrees. As in, the returning vector will be
        //rotated within 45 degrees clockwise or counter-clockwise for scew = 1;
        sourceVec = sourceVec.normalize();
        return new Point2D(
                sourceVec.getX() + (scew * (Main.random.nextDouble() - 0.5)),
                sourceVec.getY() + (scew * (Main.random.nextDouble() - 0.5))
        ).normalize();
    }

    public static Point2D rotateVector(Point2D sourceVec, double degrees){
        //TODO: Impliment this function instead
        //x2 = cos(β)x1 − sin(β)y1 , where β is "angle
        //y2 = sin(β)x1 + cos(β)y1
        double rotation = degrees * (1 / 90D);
        sourceVec = sourceVec.normalize();
        return new Point2D(
                sourceVec.getX() + rotation,
                sourceVec.getY() + rotation
        ).normalize();
    }

    public static Point2D getRandomVector(){
        return new Point2D(
                Main.random.nextDouble() - 0.5,
                Main.random.nextDouble() - 0.5
        ).normalize();
    }

}
