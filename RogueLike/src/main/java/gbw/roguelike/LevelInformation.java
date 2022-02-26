package gbw.roguelike;

import gbw.roguelike.utility.IllegalLevelInformationException;
import javafx.geometry.Point2D;

import java.util.Random;

public class LevelInformation {

    private final static Random random = new Random();

    private final Point2D roomCountRange;
    private final Point2D roomIdRange;
    private final int id, maxRooms, specialRooms;

    public LevelInformation(int id, Point2D roomCountRange, Point2D roomIdRange, int specialRooms){
        this.roomCountRange = checkValues(roomCountRange, "Invalid Room Count Range found in LevelConfig.txt");
        this.roomIdRange = checkValues(roomIdRange, "Invalid Room ID Range found in LevelConfig.txt");
        this.id = id;
        this.maxRooms = calcMaxRooms();
        this.specialRooms = specialRooms;
    }

    private Point2D checkValues(Point2D p, String s){
        Point2D toReturn = p;

        if(p.getX() < 1 || p.getY() < 1){
            System.err.println(s);
            System.exit(101);
        }

        return toReturn;
    }

    private int calcMaxRooms() {
        if((int) (roomIdRange.getX() - roomIdRange.getY()) == 0){
            return (int) roomIdRange.getX();
        }
        return (int) roomCountRange.getX() + random.nextInt((int) (roomCountRange.getX() - roomCountRange.getY()));
    }

    public int getNextRoomId(){
        if(roomIdRange.getX() - roomIdRange.getY() == 0){
            return 1;
        }
        return random.nextInt((int) roomIdRange.getY(), (int) roomIdRange.getX() + 1);
    }

    public int getId(){
        return id;
    }

    public int getMaxRooms(){
        return maxRooms;
    }

}
