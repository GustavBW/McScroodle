package gbw.tdg.towerdefensegame;

import java.util.HashSet;
import java.util.Set;

public interface Tickable extends IGameObject{

    Set<Tickable> active = new HashSet<>();
    Set<Tickable> newborn = new HashSet<>();
    Set<Tickable> expended = new HashSet<>();

    void tick();
}
