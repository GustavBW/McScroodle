package gbw.roguelike.enums;

public enum ResistanceType {

    BLUDGEONING(1),
    PIERCING(2),
    SLASHING(3),

    FORCE(4),
    RADIANT(5),
    NECROTIC(6),

    FIRE(7),
    COLD(8),
    PSYCHIC(9);

    public int index;

    ResistanceType(int index){
        this.index = index;
    }
}
