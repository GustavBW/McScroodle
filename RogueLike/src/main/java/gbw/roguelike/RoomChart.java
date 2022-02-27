package gbw.roguelike;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RoomChart {

    private final int width;
    private final int height;
    private final int offsetX, offsetY, increment;
    private final Room[][] chart;

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

    }

    public boolean add(Room room){
        if(!isValidPlacement(room)){
           return false;
        }

        //Translating units from "pixel-space" to chart space.
        int roomX = (int) (room.getPosition().getX() / increment) + offsetX;
        int roomY = (int) (room.getPosition().getY() / increment) + offsetY;
        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        //Then filling all those spots with the room
        for(int cY = 0; cY <= roomH; cY++){

            for(int cX = 0; cX <= roomW; cX++){

                chart[roomY + cY][roomX + cX] = room;
            }
        }

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

        if(width <= countStart + howMany){
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
