package gbw.roguelike.backend;

import gbw.roguelike.LevelInformation;
import gbw.roguelike.NPCInfo;
import gbw.roguelike.Room;
import gbw.roguelike.RoomExit;
import gbw.roguelike.enums.AttackAnimationType;
import gbw.roguelike.enums.MovementAnimationTypes;
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
    public static HashMap<MovementAnimationTypes, Image[]> getPlayerGraphics(){
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

    public static HashMap<AttackAnimationType, Image[]> getWeaponAnimations(String name) {
        return gp.getWeaponAnimations(name);
    }

    public static Image getItemGraphics(int id) {
        return gp.getItemGraphics(id);
    }

    public static ArrayList<String> getNPCLines(int id) {
        return tp.getNPCLines(id);
    }

    public static NPCInfo getNPCInfo(int id) {
        return tp.getNPCInfo(id);
    }

    public static Image getNPCImage(int id) {
        return gp.getNPCGraphics(id);
    }
}
