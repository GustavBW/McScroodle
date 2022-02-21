package gbw.roguelike;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Room implements Renderable {

    private final Image[] baseImages;
    private final Point2D position;
    private final int[][] boundaries;
    private int id;


    public Room(int id, Point2D position){
        this.id = id;
        this.position = position;
        baseImages = ContentEngine.getRoomGraphics(id);
        boundaries = calcVisualBoundaries();
    }


    @Override
    public void render(GraphicsContext gc) {
        for(Image i : baseImages){
            gc.drawImage(i, position.getX() + WorldSpace.worldSpaceOffset.getX(), position.getY() + WorldSpace.worldSpaceOffset.getY());
        }
    }

    public int[][] getBoundaries(){
        return boundaries;
    }

    private int[][] calcVisualBoundaries(){
        Image bI1 = baseImages[0];
        return new int[][]{
                {(int) position.getX(),(int) position.getY()},
                {(int) (position.getX() + bI1.getWidth()),(int) position.getY()},
                {(int) position.getX(),(int) (position.getY() + bI1.getHeight())},
                {(int) (position.getX() + bI1.getWidth()),(int) (position.getY() + bI1.getHeight())}
        };
    }
}
