package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface Renderable {

    void render(GraphicsContext gc, Point2D worldSpaceOffset);


}
