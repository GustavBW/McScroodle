package gbw.roguelike.enums;

public enum ExitDirection {

    NORTH("north", 0.0D,-1.0D, "south"),
    EAST("east", 1.0D,0.0D, "west"),
    SOUTH("south", 0.0D,1.0D, "north"),
    WEST("west", -1.0D, 0.0D, "east");

    public String direction;
    public double xOffset;
    public double yOffset;
    public String opposite;

    ExitDirection(String direction, double xOffset, double yOffset, String opposite){
        this.direction = direction;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.opposite = opposite;
    }

}
