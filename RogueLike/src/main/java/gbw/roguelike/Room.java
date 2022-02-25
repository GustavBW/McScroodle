package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Room extends GameObject implements Renderable {

    private final Image[] baseImages;
    private final Point2D position;
    private final int[][] boundaries;
    private ArrayList<RoomExit> exits;
    private int id;


    public Room(int id, Point2D position){
        this.id = id;
        this.position = position;

        baseImages = ContentEngine.getRoomGraphics(id);
        exits = ContentEngine.getRoomExits(id);
        boundaries = calcVisualBoundaries();

        giveExitsRoomPosition();
    }


    @Override
    public void render(GraphicsContext gc) {
        for(Image i : baseImages){
            gc.drawImage(i, position.getX() + WorldSpace.worldSpaceOffset.getX(), position.getY() + WorldSpace.worldSpaceOffset.getY());
        }
        for(RoomExit r : exits){
            r.render(gc);
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
                {(int) (position.getX() + bI1.getWidth()),(int) (position.getY() + bI1.getHeight())},
                {(int) (position.getX() + (bI1.getWidth() * 0.5)), (int) (position.getY() + (bI1.getHeight() * 0.5))}   //The middle of the image
        };
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Point2D getSize() {
        Image bI1 = baseImages[0];
        return new Point2D(bI1.getWidth(), bI1.getHeight());
    }

    private void giveExitsRoomPosition(){
        for(RoomExit r : exits){
            r.setRoomPos(position);
        }
    }
}
