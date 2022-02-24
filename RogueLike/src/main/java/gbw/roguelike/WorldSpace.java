package gbw.roguelike;

import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class WorldSpace implements Renderable {

    public static Point2D worldSpaceOffset = new Point2D(0,0);

    private final ArrayList<Room> allRooms;
    private final ArrayList<Room> visibleRooms;

    public WorldSpace(){
        allRooms = new ArrayList<>();
        visibleRooms = new ArrayList<>();
    }

    public void evaluateWhichRoomsAreVisible(){

        ArrayList<Room> visibleRoomsClone = (ArrayList<Room>) visibleRooms.clone();

        for(Room r : visibleRoomsClone){

            if(!roomIsVisible(r)){
                visibleRooms.remove(r);
            }
        }

        for(Room r : allRooms){
            if(roomIsVisible(r)){
                visibleRooms.add(r);
            }
        }
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

    public boolean addRoom(Room r){
        return allRooms.add(r);
    }
}
