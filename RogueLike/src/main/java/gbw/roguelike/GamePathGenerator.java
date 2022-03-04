package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.enums.ExitDirection;
import gbw.roguelike.interfaces.Tickable;
import javafx.geometry.Point2D;

import java.util.*;

public class GamePathGenerator implements Tickable {

    private static final Random random = new Random(System.nanoTime());
    private static int currentLevel = 1;
    private static boolean levelChange = false;
    private static HashMap<Integer, RoomChart> storedLevels;
    private static ArrayList<LevelInformation> levelInformations;
    private final WorldSpace worldSpace;
    private final Minimap minimap;

    public GamePathGenerator(WorldSpace wS, Minimap minimap){
        storedLevels = new HashMap<>();
        levelInformations = ContentEngine.getLevelInformations();
        this.worldSpace = wS;
        this.minimap = minimap;
        Tickable.tickables.add(this);
    }

    public static RoomChart generateLevel(int level){

        Room startingRoom = new Room(1, new Point2D(0,0));
        LevelInformation levelInfo = getLevelInformation(level);
        int roomCount = levelInfo.getMaxRooms();
        RoomChart output = new RoomChart(roomCount * 2, roomCount *2, 200);

        output.addRaw(startingRoom);
        System.out.println("Generating Level: " + level + " with " + roomCount + " rooms");

        Room currentRoom;
        Point2D roomPos;

        for(int i = 0; i < roomCount + 1; i++){
            currentRoom = new Room(levelInfo.getNextRoomId(), new Point2D(0,0));
            if(!output.addRoomAtRandom(currentRoom,random.nextInt())){
                i--;
            }
        }

        for(Room room : output.getAsArrayList()){
            room.addAdjacentRooms(output.getNeighboors(room));
        }

        storedLevels.put(currentLevel,output);
        System.out.println("Level " + currentLevel + " generated with " + output.getAsArrayList().size() + " rooms");
        //output.print();
        return output;
    }

    private static Point2D calcNewRoomPosition(RoomExit exitToMatch, RoomExit exitFound, Room room) {

        Point2D vecBetween = exitToMatch.getPosition().subtract(exitFound.getPosition());

        return room.getPosition().add(vecBetween);
    }

    private static ExitDirection getOppositeExitDirection(RoomExit currentEvaluatedExit) {

        for(ExitDirection e : ExitDirection.values()){
            if(e.direction == currentEvaluatedExit.getDirection().opposite){
                return e;
            }
        }

        return ExitDirection.NORTH; //Default parameter
    }

    private static LevelInformation getLevelInformation(int level) {
        for(LevelInformation l : levelInformations){
            if(l.getId() == level){
                return l;
            }
        }
        return levelInformations.get(0);
    }

    public static void setlevel(int newLevel){
        if(storedLevels.get(newLevel) == null){
            storedLevels.put(newLevel, generateLevel(newLevel));
        }

        currentLevel = newLevel;
        levelChange = true;
    }

    public static int getCurrentLevel(){return currentLevel;}

    @Override
    public void tick() {
        if(levelChange){
            worldSpace.onLevelChange(storedLevels.get(currentLevel));
            System.out.println("Drawing minimap for level " + currentLevel);
            minimap.drawRoomChart(storedLevels.get(currentLevel));
            levelChange = false;
        }
    }

}
