package gbw.roguelike.enums;

public enum ExitDirection {

    NORTH(FacingDirection.NORTH, 0.0D,-1.0D, FacingDirection.SOUTH),
    EAST(FacingDirection.EAST, 1.0D,0.0D, FacingDirection.WEST),
    SOUTH(FacingDirection.SOUTH, 0.0D,1.0D, FacingDirection.NORTH),
    WEST(FacingDirection.WEST, -1.0D, 0.0D, FacingDirection.EAST);

    public FacingDirection direction;
    public double xOffset;
    public double yOffset;
    public FacingDirection opposite;

    ExitDirection(FacingDirection direction, double xOffset, double yOffset, FacingDirection opposite){
        this.direction = direction;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.opposite = opposite;
    }

}
