package gbw.roguelike;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoomChart {

    private final int width;
    private final int height;
    private final int offsetX, offsetY, increment;
    private final Room[][] chart;
    private final ArrayList<Room> quickChart;

    //The RoomChart is used to map rooms in relation to each other in 2 dimensions.
    //The primary function is to find neighbooring rooms and thus it's scaled down
    //Compared to a pixel repressentation of the rooms in relation to each other.
    //increment is the lowest significant width / height of any room added. This is the scaling factor
    //width & height is used to compensate for the fact that ArrayLists can't "expand" in negative directions
    //thus all positions is locally offset. Any negative position values are thus invalid but used to
    //communicate if a room wasn't found and the likes.

    public RoomChart(int width, int height, int increment){
        this.increment = increment;
        this.width = width;
        this.height = height;
        this.offsetX = width / 2;
        this.offsetY = height / 2;
        this.chart = new Room[height + 1][width + 1];
        this.quickChart = new ArrayList<>();
    }

    public boolean addRaw(Room room){
        if(!isValidPlacement(room)){
           return false;
        }

        //Translating units from "pixel-space" to chart space.
        int roomX = (int) (room.getPosition().getX() / increment) + offsetX;
        int roomY = (int) (room.getPosition().getY() / increment) + offsetY;
        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        //Then filling all those spots with the room
        for(int cY = 0; cY < roomH; cY++){

            for(int cX = 0; cX < roomW; cX++){

                chart[roomY + cY][roomX + cX] = room;
            }
        }
        quickChart.add(room);

        return true;
    }

    public boolean remove(Room room){
        int[] chartPos = getRoomPositionInChart(room);
        if(chartPos[0] == -1 || chartPos[1] == -1){
            return false;
        }

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(chart[y][x] == room){
                    chart[y][x] = null;
                }
            }
        }
        quickChart.remove(room);

        return true;
    }

    public boolean isValidPlacement(Room room){
        //Translating units from "pixel-space" to chart space.
        int roomX = (int) (room.getPosition().getX() / increment) + offsetX;
        int roomY = (int) (room.getPosition().getY() / increment) + offsetY;
        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        if(roomX + roomW > width || roomY + roomH > height){
            System.err.println("RoomChart | index Y: " + roomY + " X: " + roomX + ". Is out of bounds for W: " + width + " H: " + height);
            return false;
        }

        //Then checking if any of those spaces are already occupied.
        for(int cY = 0; cY < roomH; cY++){

            for(int cX = 0; cX < roomW; cX++){

                if(chart[roomY + cY][roomX + cX] != null){
                    return false;
                }
            }
        }

        return true;
    }

    public Point2D findValidRandomPlacement(Room room, int seed){
        //This function finds the position closest to another room, that is valid.
        Point2D output = null;
        ArrayList<Room> copy = new ArrayList<>(quickChart);

        if(copy.isEmpty()){
            System.err.println("RoomChart | Invalid Method Call |  findValidRandomPlacement() requires a non-empty roomChart");
            return null;
        }

        Random random = new Random();
        Room current;

        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        while(copy.size() >= 1){
            current = copy.size() <= 1 ? copy.get(0) : copy.get(random.nextInt(copy.size()));

            int[] r1Pos = getRoomPositionInChart(current);
            int r1W = (int) current.getSize().getX() / increment;
            int r1H = (int) current.getSize().getY() / increment;

            for(int i = 0; i < 4; i ++) {
                switch ((seed + i) % 4) {
                    //To the left
                    case 0 -> output = new Point2D(((r1Pos[0] - roomW) - offsetX) * increment, (r1Pos[1] - offsetY) * increment);
                    //On top
                    case 1 -> output = new Point2D((r1Pos[0] - offsetX) * increment, ((r1Pos[1] - roomH) - offsetY) * increment);
                    //To the right
                    case 2 -> output = new Point2D(((r1Pos[0] + r1W) - offsetX) * increment, (r1Pos[1] - offsetY) * increment);
                    //Below
                    case 3 -> output = new Point2D((r1Pos[0] - offsetX) * increment, ((r1Pos[1] + r1H) - offsetY) * increment);
                }

                room.setPosition(output);
                if (isValidPlacement(room)) {
                    return output;
                }
            }
            copy.remove(current);
        }

        return output;
    }

    public ArrayList<Room> getNeighboors(Room room){
        int[] chartPos = getRoomPositionInChart(room);
        Set<Room> output = new HashSet<>();

        if(chartPos[0] == -1 || chartPos[1] == -1){
            return new ArrayList<>(output);
        }

        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        output.addAll(getRoomsInColumn(chartPos[0] - 1, chartPos[1], roomH));
        output.addAll(getRoomsInColumn(chartPos[0] + roomW + 1, chartPos[1], roomH));

        output.addAll(getRoomsInRow(chartPos[1] - 1, chartPos[0], roomW));
        output.addAll(getRoomsInRow(chartPos[1] + roomH + 1, chartPos[0], roomW));

        return new ArrayList<>(output);
    }

    private ArrayList<Room> getRoomsInColumn(int colNum, int countStart, int howMany){
        ArrayList<Room> output = new ArrayList<>();

        if(height < countStart + howMany){
            System.err.println("RoomChart | Inaccessible values | Requested: " + "[" + (countStart + howMany) + "," + colNum + "]");
            return output;
        }

        for(int cY = 0; cY < howMany; cY++){

            if(chart[cY + countStart][colNum] != null) {
                output.add(chart[cY + countStart][colNum]);
            }
        }

        return output;
    }

    private ArrayList<Room> getRoomsInRow(int rowNum, int countStart, int howMany){
        ArrayList<Room> output = new ArrayList<>();

        if(width < countStart + howMany){
            System.err.println("RoomChart | Inaccessible values | Requested: " + "[" + (countStart + howMany) + "," + rowNum + "]");
            return output;
        }

        for(int cX = 0; cX < howMany; cX++){
            if(chart[rowNum][cX + countStart] != null){
                output.add(chart[rowNum][cX + countStart]);
            }
        }

        return output;
    }

    public boolean isValidPoint(int x, int y){
        if((x < width && y < height) && (x >= 0 && y >= 0)){
            return true;
        }
        return false;
    }

    public Room getClosestRoomTo(Point2D p){
        int tPx = (int) (p.getX() / increment) + offsetX;
        int tPy = (int) (p.getY() / increment) + offsetY;

        Room closest = null;

        if(!isValidPoint(tPx, tPy)){
            return closest;
        }

        if(chart[tPy][tPx] != null){
            return chart[tPy][tPx];
        }

        closest = ringSearch(tPx, tPy);

        return closest;
    }

    private Room ringSearch(int tPx, int tPy) {
        int offset = 1;

        while(isValidPoint(tPx + offset, tPy + offset) && isValidPoint(tPx - offset, tPy - offset)){

            ArrayList<Room> upperRow = getRoomsInRow(tPy - offset, tPx - offset, 2 * offset);
            if(!upperRow.isEmpty()){
                return upperRow.get(0);
            }

            ArrayList<Room> rightColumn = getRoomsInColumn(tPx + offset, tPy - offset, 2 * offset);
            if(!rightColumn.isEmpty()){
                return rightColumn.get(0);
            }

            ArrayList<Room> lowerRow = getRoomsInRow(tPy + offset, tPx - offset, 2 * offset);
            if(!lowerRow.isEmpty()){
                return lowerRow.get(0);
            }

            ArrayList<Room> leftColumn = getRoomsInColumn(tPx - offset, tPy - offset, 2 * offset);
            if(!leftColumn.isEmpty()){
                return leftColumn.get(0);
            }

            offset++;
        }

        return null;
    }

    public int[] getRoomPositionInChart(Room room){
        int[] output = {-1,-1};

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(chart[y][x] == room){
                    output[0] = x;
                    output[1] = y;
                    return output;
                }
            }
        }
        System.err.println("RoomChart 404 | Room Not Found | Room " + room + " was not located in the chart");
        return output;
    }

    public int[] getMiddle(Room room){
        int[] output = getRoomPositionInChart(room);

        output[0] += (int) (room.getSize().getX() / increment) / 2;
        output[1] += (int) (room.getSize().getY() / increment) / 2;

        return output;
    }

    public ArrayList<Room> getAsArrayList(){
        return new ArrayList<>(quickChart);
    }

    public void print(){

        String spacer = "__________";

        System.out.println("CHART " + this);
        System.out.println();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(chart[y][x] == null) {
                    System.out.print(spacer);
                }else{
                    System.out.print("| " + chart[y][x] + " |");
                }
            }
            System.out.println();
            for(int i = 0; i < width; i++){
                System.out.print(spacer);
            }
            System.out.println();
        }

    }
}
