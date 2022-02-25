package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.enums.ExitDirection;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Room extends GameObject implements Renderable {

    private final Image[] baseImages;
    private Point2D position;
    private final Point2D size;
    private final int[][] boundaries;
    private ArrayList<RoomExit> exits;
    private final ArrayList<Room> adjacentRooms;
    private int id;


    public Room(int id, Point2D position){
        this.id = id;
        this.position = position;
        this.adjacentRooms = new ArrayList<>();

        baseImages = ContentEngine.getRoomGraphics(id);
        exits = ContentEngine.getRoomExits(id, this);
        boundaries = calcVisualBoundaries();
        this.size = getSize();

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
                //{(int) position.getX(),(int) position.getY()},
                //{(int) (position.getX() + bI1.getWidth()),(int) position.getY()},
                //{(int) position.getX(),(int) (position.getY() + bI1.getHeight())},
                //{(int) (position.getX() + bI1.getWidth()),(int) (position.getY() + bI1.getHeight())},
                {(int) (position.getX() + (bI1.getWidth() * 0.5)), (int) (position.getY() + (bI1.getHeight() * 0.5))}   //The middle of the image
        };
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D p){this.position = p;}

    @Override
    public Point2D getSize() {
        Image bI1 = baseImages[0];
        return new Point2D(bI1.getWidth(), bI1.getHeight());
    }

    public ArrayList<RoomExit> getExits(){
        return exits;
    }

    public boolean isInBounds(Point2D pos){
        Image bI1 = baseImages[0];

        boolean isWithInImageBounds = (pos.getX() < position.getX() + WorldSpace.worldSpaceOffset.getX() + size.getX() && pos.getX() > position.getX() + WorldSpace.worldSpaceOffset.getX())
                                        &&
                                    (pos.getY() < position.getY() + WorldSpace.worldSpaceOffset.getY() + size.getY() && pos.getY() > position.getY() + WorldSpace.worldSpaceOffset.getY());

        if(!isWithInImageBounds){
            return false;
        }

        //Then normalize the positions to the same reference frame and check the pixel value
        //pos = pos.add(position.multiply(-1));
        //return bI1.getPixelReader().getColor((int) pos.getX(), (int) pos.getY()).getOpacity() > 0.05;
        return true;
    }

    private void giveExitsRoomPosition(){
        for(RoomExit r : exits){
            r.setRoomPos(position);
        }
    }
    public ArrayList<Room> getAdjacentRooms(){
        return adjacentRooms;
    }
    public boolean addAdjacentRoom(Room room){
        boolean success = false;

        if(!adjacentRooms.contains(room)){
            adjacentRooms.add(room);
            success = true;
        }
        return success;
    }

    public boolean hasExitByDirection(ExitDirection e){
        for(RoomExit r : exits){
            if(r.getDirection().direction == e.direction){
                return true;
            }
        }
        return false;
    }

    public int getId(){
        return id;
    }

    public RoomExit getExitByDirection(ExitDirection exitToMatch) {
        for(RoomExit r : exits){
            if(r.getDirection().direction == exitToMatch.direction){
                return r;
            }
        }
        return null;
    }
}
