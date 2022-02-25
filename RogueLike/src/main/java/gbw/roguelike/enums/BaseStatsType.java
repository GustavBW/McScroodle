package gbw.roguelike.enums;

public enum BaseStatsType {

    SPEED_MOVEMENT("SPEED_MOVEMENT"),
    SPEED_ANIMATION("SPEED_ANIMATION"),

    HEALTH("HEALTH"),
    HEALTH_MAX("HEALTH_MAX"),
    HEALTH_TEMPORARY("HEALTH_TEMPORARY"),

    UNKNOWN("YOU_DONE_GOOFED");

    public String alias;

    BaseStatsType(String alias){
        this.alias = alias;
    }

}
