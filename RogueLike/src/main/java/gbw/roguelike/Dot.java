package gbw.roguelike;

import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Dot {

    private final Point2D position;
    private final double sizeX;
    private final double sizeY;

    public Dot(Point2D position, double sizeX, double sizeY) {
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void render(GraphicsContext gc, Point2D translatedWSO) {
        gc.fillRect(position.getX() + translatedWSO.getX(), position.getY() + translatedWSO.getY(),sizeX,sizeY);
    }
}
