package gbw.roguelike.enums;

public enum AnimationType {

    WALKING_NORTH("walkingNorth"),
    WALKING_SOUTH("walkingSouth"),
    WALKING_EAST("walkingEast"),
    WALKING_WEST("walkingWest"),

    BEING_HURT("beingHurt"),
    ATTACK_MELEE("attackMelee"),
    IDLE("idle"),
    UNKNOWN("youDoneGoofed");

    public String name;

    AnimationType(String name){
        this.name = name;
    }

}
