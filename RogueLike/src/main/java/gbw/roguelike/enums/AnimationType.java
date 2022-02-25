package gbw.roguelike.enums;

public enum AnimationType {

    WALKING_NORTH("walkingNorth", FacingDirection.NORTH),
    WALKING_SOUTH("walkingSouth", FacingDirection.SOUTH),
    WALKING_EAST("walkingEast", FacingDirection.EAST),
    WALKING_WEST("walkingWest", FacingDirection.WEST),

    BEING_HURT("beingHurt", FacingDirection.NON_DIRECTIONAL),
    ATTACK_MELEE("attackMelee", FacingDirection.NON_DIRECTIONAL),
    IDLE("idle", FacingDirection.NON_DIRECTIONAL),
    UNKNOWN("youDoneGoofed", FacingDirection.NON_DIRECTIONAL);

    public String name;
    public FacingDirection direction;

    AnimationType(String name, FacingDirection direction){
        this.name = name;
        this.direction = direction;
    }

}
