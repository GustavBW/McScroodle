package gbw.roguelike.enums;

public enum FacingDirection {

    NORTH("north",0, 0, -1),
    SOUTH("south",180, 0,1),
    EAST("east",90, 1, 0),
    WEST("west",270, -1,0),
    NON_DIRECTIONAL("You've Done Goofed", 360,1,1);

    public String alias;
    public int inDegrees;
    public int xOffset;
    public int yOffset;

    FacingDirection(String alias, int inDegrees, int xOffset, int yOffset){
        this.alias = alias;
        this.inDegrees = inDegrees;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

}
