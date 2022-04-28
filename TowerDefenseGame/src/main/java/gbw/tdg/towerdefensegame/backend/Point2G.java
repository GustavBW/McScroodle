package gbw.tdg.towerdefensegame.backend;

import gbw.tdg.towerdefensegame.Main;
import javafx.geometry.Point2D;

public abstract class Point2G {

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
        double rotation = degrees * (1 / 90D);
        sourceVec = sourceVec.normalize();
        return new Point2D(
                sourceVec.getX() + rotation,
                sourceVec.getY() + rotation
        ).normalize();
    }

}
