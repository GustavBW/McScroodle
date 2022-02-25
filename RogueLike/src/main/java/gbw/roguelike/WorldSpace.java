package gbw.roguelike;

import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class WorldSpace implements Renderable {

    public static Point2D worldSpaceOffset = new Point2D(0,0);

    private final ArrayList<Room> allRooms;
    private final ArrayList<Room> visibleRooms;
    private final Player player;
    private boolean funcInUse1 = false;

    public WorldSpace(Player player){
        allRooms = new ArrayList<>();
        visibleRooms = new ArrayList<>();
        this.player = player;
    }

    public void evaluateWhichRoomsAreVisible(){

        //remove all rooms first.
        visibleRooms.clear();

        Room theRoomThePlayerIsIn = getTheRoomThePlayerIsIn();
        makeRoomVisibleIfNotAlready(theRoomThePlayerIsIn);

        for(Room r : theRoomThePlayerIsIn.getAdjacentRooms()){
            makeRoomVisibleIfNotAlready(r);
        }
    }

    private void makeRoomVisibleIfNotAlready(Room room) {
        if(!visibleRooms.contains(room)){
            visibleRooms.add(room);
        }
    }

    private Room getTheRoomThePlayerIsIn() {
        Point2D playerPos = player.getPosition();
        Room toReturn = allRooms.get(0);

        for(Room r : allRooms){
            if(r.isInBounds(playerPos)){
                toReturn = r;
            }
        }
        return toReturn;
    }

    private boolean roomIsVisible(Room r){
        Point2D dimensions = Main.canvasDim;
        double worldOffX = worldSpaceOffset.getX();
        double worldOffY = worldSpaceOffset.getY();

        for(int[] pos : r.getBoundaries()){

            boolean eval = (pos[0] + worldOffX > 0 && pos[0] + worldOffX < dimensions.getX())
                            &&
                           (pos[1] + worldOffY > 0 && pos[1] + worldOffY < dimensions.getY());

            if(eval){
                return true;
            }
        }

        return false;
    }

    public void onLevelChange(ArrayList<Room> newRooms){
        allRooms.clear();
        visibleRooms.clear();
        allRooms.addAll(newRooms);
    }

    @Override
    public void render(GraphicsContext gc) {
        for(Room r : visibleRooms){
            r.render(gc);
        }
    }

    public ArrayList<Room> getAllRooms(){return allRooms;}

    public boolean addRoom(Room r){
        return allRooms.add(r);
    }
}
