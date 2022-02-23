package gbw.roguelike.interfaces;

import java.util.ArrayList;

public interface Tickable {

    ArrayList<Tickable> tickables = new ArrayList<>();

    void tick();

}
