package gbw.roguelike;

import javafx.beans.NamedArg;
import javafx.geometry.Point2D;

public class SpaceTranslator {

    public static int[] toChartSpace(@NamedArg("WorldSpace pos") Point2D p, RoomChart chart){

        int x = (int) Math.round((p.getX() / chart.getIncrement()) + chart.getOffsetX());
        int y = (int) Math.round((p.getY() / chart.getIncrement()) + chart.getOffsetY());

        return new int[]{x,y};
    }

    public static int[] roomDimToChartSpace(@NamedArg("Room dim.") Point2D s, RoomChart chart){

        int xDim = (int) Math.round(s.getX() / chart.getIncrement());
        int yDim = (int) Math.round(s.getY() / chart.getIncrement());

        return new int[]{xDim, yDim};
    }

    public static Point2D toWorldSpace(@NamedArg("Chart space") int x, int y, RoomChart chart){

        int xWS = (x - chart.getOffsetX()) * chart.getIncrement();
        int yWS = (y - chart.getOffsetY()) * chart.getIncrement();

        return new Point2D(xWS,yWS);
    }
}
