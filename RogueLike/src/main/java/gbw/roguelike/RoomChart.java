package gbw.roguelike;

import javafx.geometry.Point2D;

import java.util.*;

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
    //width & height is used to compensate for the fact that arrays can't naturally "expand" in negative directions
    //thus all positions is locally offset. Any negative position values are thus invalid but used to
    //communicate if a room wasn't found and the likes.

    public RoomChart(int width, int height, int increment){
        this.increment = checkIfValid(increment);
        this.width = width;
        this.height = height;
        this.offsetX = width / 2;
        this.offsetY = height / 2;
        this.chart = new Room[height + 1][width + 1];
        this.quickChart = new ArrayList<>();
    }

    private int checkIfValid(int increment) {
        if(increment <= 0){
            return 1;
        }
        return increment;
    }

    public RoomChart(RoomChart rC){
        this.increment = rC.getIncrement();
        this.width = rC.getWidth();
        this.height = rC.getHeight();
        this.offsetX = rC.getOffsetX();
        this.offsetY = rC.getOffsetY();
        this.chart = rC.getChart();
        this.quickChart = rC.getAsArrayList();
    }

    public int getIncrement() {
        return increment;
    }
    public int getOffsetY() {
        return offsetY;
    }
    public int getOffsetX(){
        return offsetX;
    }

    public boolean addRaw(Room room){
        if(!isValidPlacement(room)){
           return false;
        }

        //Translating units from "pixel-space" to chart space.
        int[] roomPos = SpaceTranslator.toChartSpace(room.getPosition(), this);
        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        //Then filling all those spots with the room
        for(int cY = 0; cY < roomH; cY++){

            for(int cX = 0; cX < roomW; cX++){

                //Filtering positions based on if there's graphical information at this position or not
                if(room.isInBoundsRaw(new Point2D(cX + (increment / 2.00), cY + (increment / 2.00)))) {
                    chart[roomPos[1] + cY][roomPos[0] + cX] = room;
                }
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
        int[] roomPos = SpaceTranslator.toChartSpace(room.getPosition(), this);

        int roomW = (int) room.getSize().getX() / increment;
        int roomH = (int) room.getSize().getY() / increment;

        if(roomPos[0] + roomW > width || roomPos[1] + roomH > height){
            System.err.println("RoomChart | index Y: " + roomPos[1] + " X: " + roomPos[0] + ". Is out of bounds for W: " + width + " H: " + height);
            return false;
        }

        if(roomPos[0] < 0 || roomPos[1] < 0){
            System.err.println("RoomChart | index Y: " + roomPos[1] + " X: " + roomPos[0] + ". Is out of bounds for W: " + width + " H: " + height);
            return false;
        }

        //Then checking if any of those spaces are already occupied.
        for(int cY = 0; cY < roomH; cY++){

            for(int cX = 0; cX < roomW; cX++){

                if(chart[roomPos[1] + cY][roomPos[0] + cX] != null){
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

        //TODO Make this take room exists into account

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
            int[] rDim = SpaceTranslator.roomDimToChartSpace(current.getSize(),this);

            for(int i = 0; i < 4; i ++) {
                switch ((seed + i) % 4) {
                    //To the left
                    case 0 -> output = SpaceTranslator.toWorldSpace((r1Pos[0] - roomW), r1Pos[1], this);
                    //On top
                    case 1 -> output = SpaceTranslator.toWorldSpace(r1Pos[0], (r1Pos[1] - roomH), this);
                    //To the right
                    case 2 -> output = SpaceTranslator.toWorldSpace((r1Pos[0] + rDim[0]), r1Pos[1], this);
                    //Below
                    case 3 -> output = SpaceTranslator.toWorldSpace(r1Pos[0], (r1Pos[1] + rDim[1]), this);
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

        int[] roomDim = SpaceTranslator.roomDimToChartSpace(room.getSize(),this);

        output.addAll(getRoomsInColumn(chartPos[0] - 1, chartPos[1], roomDim[1]));
        output.addAll(getRoomsInColumn(chartPos[0] + roomDim[0] + 1, chartPos[1], roomDim[1]));

        output.addAll(getRoomsInRow(chartPos[1] - 1, chartPos[0], roomDim[0]));
        output.addAll(getRoomsInRow(chartPos[1] + roomDim[1] + 1, chartPos[0], roomDim[0]));

        return new ArrayList<>(output);
    }

    private ArrayList<Room> getRoomsInColumn(int colNum, int countStart, int howMany){
        Set<Room> output = new HashSet<>();

        if(height <= countStart + howMany){
            System.err.println("RoomChart | Inaccessible values | Requested: " + "[" + (countStart + howMany) + "," + colNum + "]");
            return new ArrayList<>(output);
        }

        for(int cY = 0; cY <= howMany; cY++){

            if(chart[cY + countStart][colNum] != null) {
                output.add(chart[cY + countStart][colNum]);
            }
        }

        return new ArrayList<>(output);
    }

    private ArrayList<Room> getRoomsInRow(int rowNum, int countStart, int howMany){
        Set<Room> output = new HashSet<>();

        if(width <= countStart + howMany){
            System.err.println("RoomChart | Inaccessible values | Requested: " + "[" + (countStart + howMany) + "," + rowNum + "]");
            return new ArrayList<>(output);
        }

        for(int cX = 0; cX <= howMany; cX++){
            if(chart[rowNum][cX + countStart] != null){
                output.add(chart[rowNum][cX + countStart]);
            }
        }

        return new ArrayList<>(output);
    }

    public boolean isValidPoint(int x, int y){
        if((x < width && y < height) && (x >= 0 && y >= 0)){
            return true;
        }
        return false;
    }

    public Room getClosestRoomTo(Point2D p){
        int[] chartSpaceP = SpaceTranslator.toChartSpace(p,this);

        Room closest = null;

        if(!isValidPoint(chartSpaceP[0], chartSpaceP[1])){
            return closest;
        }

        if(chart[chartSpaceP[1]][chartSpaceP[0]] != null){
            return chart[chartSpaceP[1]][chartSpaceP[0]];
        }

        closest = ringSearch(chartSpaceP[0], chartSpaceP[1]);

        return closest;
    }

    public Room ringSearch(int tPx, int tPy) {
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
        int[] roomDimCS = SpaceTranslator.roomDimToChartSpace(room.getSize(),this);

        output[0] += (roomDimCS[0]) / 2;
        output[1] += (roomDimCS[1]) / 2;

        return output;
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public ArrayList<Room> getAsArrayList(){
        return new ArrayList<>(quickChart);
    }
    public Room[][] getChart(){
        return chart.clone();
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
