package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.interfaces.Tickable;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePathGenerator implements Tickable {

    private static int currentLevel = 1;
    private static boolean levelChange = false;
    private static HashMap<Integer, ArrayList<Room>> storedLevels;
    private ArrayList<LevelInformation> levelInformations;
    private WorldSpace worldSpace;

    public GamePathGenerator(WorldSpace wS){
        storedLevels = new HashMap<>();
        levelInformations = ContentEngine.getLevelInformations();
        this.worldSpace = wS;
    }

    public Room getStartingRoom(){
        return new Room(1, new Point2D(Main.canvasDim.getX() * 0.3,Main.canvasDim.getY() * 0.4));
    }

    public static ArrayList<Room> generateLevel(int level){
        ArrayList<Room> output = new ArrayList<>();




        storedLevels.put(currentLevel,output);
        return output;
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
            levelChange = false;
        }
    }
}
