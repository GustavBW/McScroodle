package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class WorldSpace implements Renderable{

    public static final Point2D worldDimensions = new Point2D(4000,4000);

    List<RenderableSquare> squares;

    public WorldSpace(){

        squares = new ArrayList<>();

        for(int i = 0; i * 100 < worldDimensions.getX(); i++){
            for(int j = 0; j * 100 < worldDimensions.getY(); j++) {
                RenderableSquare square = new RenderableSquare(new Point2D(j * 100, i * 100), 10);
                squares.add(square);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {

        for( RenderableSquare s : squares){

            s.render(gc);

        }
    }
}
