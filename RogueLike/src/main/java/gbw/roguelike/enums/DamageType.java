package gbw.roguelike.enums;

public enum DamageType {

    BLUDGEONING(1, "bludgeoning"),
    PIERCING(2, "piercing"),
    SLASHING(3, "slashing"),
    CRUSHING(4, "crushing"),

    FORCE(5, "force"),
    RADIANT(6, "radiant"),
    NECROTIC(7, "necrotic"),

    FIRE(8, "fire"),
    COLD(9, "cold"),
    PSYCHIC(10, "psychic"),

    TRUE(11, "true"),
    VOID(12, "void");

    public int index;
    public String alias;

    DamageType(int index, String alias){
        this.index = index;
        this.alias = alias;
    }

}
