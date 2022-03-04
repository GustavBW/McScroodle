package gbw.roguelike;

import javafx.beans.NamedArg;
import javafx.geometry.Point2D;

public class SpaceTranslator {

    public static Point2D minimapToScreenFactor = new Point2D(0,0);

    public static int[] toChartSpace(@NamedArg("WorldSpace pos") Point2D p, RoomChart chart){
        //FROM world space TO RoomChart space
        int x = (int) Math.floor((p.getX() / chart.getIncrement()) + chart.getOffsetX());
        int y = (int) Math.floor((p.getY() / chart.getIncrement()) + chart.getOffsetY());

        return new int[]{x,y};
    }

    public static int[] roomDimToChartSpace(@NamedArg("Room dim.") Point2D s, RoomChart chart){
        //FROM world space TO RoomChart space
        int xDim = (int) Math.floor(s.getX() / chart.getIncrement());
        int yDim = (int) Math.floor(s.getY() / chart.getIncrement());

        return new int[]{xDim, yDim};
    }

    public static Point2D toWorldSpace(@NamedArg("Chart space") int x, int y, RoomChart chart){
        //FROM RoomChart space TO world space
        double xWS = Math.floor((x - chart.getOffsetX()) * chart.getIncrement());
        double yWS = Math.floor((y - chart.getOffsetY()) * chart.getIncrement());

        return new Point2D(xWS,yWS);
    }
    public static Point2D toLocalRoomSpace(@NamedArg("Chart space") double x, double y, RoomChart chart){
        //FROM RoomChart-Space TO Room space (relatively)
        //Subtracts the offset to bring the point relatively towards 0,0 in room space.
        //Then times the increment to scale back out into a "pixel representation".

        double xRS = Math.floor((x - chart.getOffsetX()) * chart.getIncrement());
        double yRS = Math.floor((y - chart.getOffsetY()) * chart.getIncrement());

        return new Point2D(xRS, yRS);
    }
}
