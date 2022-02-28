package gbw.roguelike;

import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class WorldSpace implements Renderable {

    public static Point2D worldSpaceOffset = new Point2D(0,0);

    private final ArrayList<Room> allRooms;
    private RoomChart roomChart;
    private final ArrayList<Room> visibleRooms;
    private final Player player;

    public WorldSpace(Player player){
        allRooms = new ArrayList<>();
        visibleRooms = new ArrayList<>();
        this.player = player;
    }

    public void evaluateWhichRoomsAreVisible(){

        //remove all rooms first.
        visibleRooms.clear();

        Room theRoomThePlayerIsIn = roomChart.getClosestRoomTo(player.getPosition().add(worldSpaceOffset.getX(), worldSpaceOffset.getY()));
        makeRoomVisibleIfNotAlready(theRoomThePlayerIsIn);
        visibleRooms.addAll(roomChart.getAsArrayList());
    }

    private void makeRoomVisibleIfNotAlready(Room room) {
        if(!visibleRooms.contains(room)){
            visibleRooms.add(room);
        }
    }

    public void onLevelChange(RoomChart newRooms){
        allRooms.clear();
        visibleRooms.clear();
        allRooms.addAll(newRooms.getAsArrayList());
        roomChart = newRooms;
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
