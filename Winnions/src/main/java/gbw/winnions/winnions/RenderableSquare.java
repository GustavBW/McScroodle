package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RenderableSquare implements Renderable{

    private Point2D position;
    private double size;

    public RenderableSquare(Point2D pos, double size){
        this.position = pos;
        this.size = size;
    }


    @Override
    public void render(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(position.getX() - size / 2, position.getY() - size / 2, size,size);

    }
}
