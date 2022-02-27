package gbw.roguelike.enums;

public enum MovementAnimationTypes {

    WALKING("walking", FacingDirection.NORTH),

    IDLE("idle", FacingDirection.NON_DIRECTIONAL),
    UNKNOWN("youDoneGoofed", FacingDirection.NON_DIRECTIONAL);

    public String name;
    public FacingDirection direction;

    MovementAnimationTypes(String name, FacingDirection direction){
        this.name = name;
        this.direction = direction;
    }

}
