package gbw.tdg.towerdefensegame;

public enum TargetingType {

    FIRST("First"),
    LAST("Last"),
    BEEFIEST("Beefiest"),
    WEAKEST("Weakest"),
    FASTEST("Fastest"),
    RANDOM("Random"),
    CLOSEST("Closest"),
    FURTHEST("Furthest");


    public String asString;

    TargetingType(String asString){
        this.asString = asString;
    }
}
