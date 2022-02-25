package gbw.roguelike.backend;

import gbw.roguelike.LevelInformation;
import gbw.roguelike.Room;
import gbw.roguelike.RoomExit;
import gbw.roguelike.enums.AnimationType;
import gbw.roguelike.enums.BaseStatsType;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentEngine {

    public static final String projRootToRessourceDir = "src/main/resources";

    private static GraphicsProcessor gp = new GraphicsProcessor();
    private static TextProcessor tp = new TextProcessor();

    public static Image[] getRoomGraphics(int id){
        return gp.getRoomGraphics(id);
    }
    public static HashMap<AnimationType, Image[]> getPlayerGraphics(){
        return gp.getPlayerGraphics();
    }

    public static Image getGraphicsNotFound(){
        return gp.getGraphicsNotFound();
    }

    public static ArrayList<RoomExit> getRoomExits(int id, Room room) {
        return tp.getRoomExits(id, room);
    }

    public static ArrayList<LevelInformation> getLevelInformations() {
        return tp.getLevelInformations();
    }

    public static HashMap<BaseStatsType, Double> getPlayerBaseStats() {
        return tp.getPlayerBaseStats();
    }
}