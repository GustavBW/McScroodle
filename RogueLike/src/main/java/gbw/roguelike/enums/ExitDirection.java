package gbw.roguelike.enums;

public enum ExitDirection {

    NORTH("north", 0.0D,-1.0D),
    EAST("east", 1.0D,0.0D),
    SOUTH("south", 0.0D,1.0D),
    WEST("west", -1.0D, 0.0D);

    public String direction;
    public double xOffset;
    public double yOffset;

    ExitDirection(String direction, double xOffset, double yOffset){
        this.direction = direction;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

}
