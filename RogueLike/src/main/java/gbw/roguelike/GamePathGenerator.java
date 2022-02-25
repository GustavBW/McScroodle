package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.enums.ExitDirection;
import gbw.roguelike.interfaces.Tickable;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GamePathGenerator implements Tickable {

    public static HashMap<Integer, ArrayList<RoomExit>> exitsInLevel = new HashMap<>();
    private static Random random = new Random(420);
    private static int currentLevel = 1;
    private static boolean levelChange = false;
    private static HashMap<Integer, ArrayList<Room>> storedLevels;
    private static ArrayList<LevelInformation> levelInformations;
    private WorldSpace worldSpace;

    public GamePathGenerator(WorldSpace wS){
        storedLevels = new HashMap<>();
        levelInformations = ContentEngine.getLevelInformations();
        this.worldSpace = wS;
        Tickable.tickables.add(this);
    }

    public Room getStartingRoom(){
        return new Room(1, new Point2D(Main.canvasDim.getX() * 0.3,Main.canvasDim.getY() * 0.4));
    }

    public static ArrayList<Room> generateLevel(int level){

        System.out.println("Generating Level: " + level);
        ArrayList<Room> output = new ArrayList<>();
        Room startingRoom = new Room(1, Main.canvasDim.multiply(0.4));
        output.add(startingRoom);

        LevelInformation levelInfo = getLevelInformation(level);

        ArrayList<RoomExit> exitPool = new ArrayList<>();
        exitPool.addAll(startingRoom.getExits());
        RoomExit currentEvaluatedExit;
        RoomExit currentMatchingExit;
        Room currentEvaluatedRoom;
        int previousEvaluatedRoomId = startingRoom.getId();

        ExitDirection oppositeExitDirectionOfCurrent;

        for(int i = 0; i < levelInfo.getMaxRooms(); i++){
            currentEvaluatedExit = exitPool.get(random.nextInt(exitPool.size() - 1));
            oppositeExitDirectionOfCurrent = getOppositeExitDirection(currentEvaluatedExit);

            currentEvaluatedRoom = new Room(levelInfo.getNextRoomId(), new Point2D(0,0));

            //Make sure to get a room that has the proper opposing exit
            while((currentMatchingExit = currentEvaluatedRoom.getExitByDirection(oppositeExitDirectionOfCurrent)) == null) {
                currentEvaluatedRoom = new Room(levelInfo.getNextRoomId(), new Point2D(0,0));
            }
            output.add(currentEvaluatedRoom);

            currentEvaluatedRoom.setPosition(calcNewRoomPosition(currentEvaluatedExit, currentMatchingExit, currentEvaluatedRoom));
            currentEvaluatedRoom.addAdjacentRoom(currentEvaluatedExit.getRoom());
            currentEvaluatedExit.getRoom().addAdjacentRoom(currentEvaluatedRoom);

            exitPool.remove(currentEvaluatedExit);
            exitPool.addAll(currentEvaluatedRoom.getExits());

            //Remove all other exits leaving only 1 "un-connected" exit
            while(currentEvaluatedRoom.getExits().size() > 1) {
                int whichExitIndex = random.nextInt(0,currentEvaluatedRoom.getExits().size());
                exitPool.remove(currentEvaluatedRoom.getExits().get(whichExitIndex));
                currentEvaluatedRoom.getExits().remove(whichExitIndex);
            }
            previousEvaluatedRoomId = currentEvaluatedRoom.getId();
        }

        exitsInLevel.get(level).addAll(exitPool);
        storedLevels.put(currentLevel,output);
        return output;
    }

    private static Point2D calcNewRoomPosition(RoomExit exitToMatch, RoomExit exitFound, Room room) {
        Point2D posOfExitToMatch = exitToMatch.getPosition();
        Point2D posOfExitFound = exitFound.getPosition();

        Point2D vecBetween = posOfExitToMatch.subtract(posOfExitFound);
        Point2D currentRoomPosition = room.getPosition();

        return currentRoomPosition.add(vecBetween);
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
            exitsInLevel.put(newLevel, new ArrayList<>());
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
            levelChange = false;
        }
    }
}
