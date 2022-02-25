package gbw.roguelike.enums;

public enum FacingDirection {

    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west"),
    NON_DIRECTIONAL("non-directional");

    public String alias;

    FacingDirection(String alias){
        this.alias = alias;
    }

}
