package gbw.gravityslingshot.gravityslingshot;

import java.util.ArrayList;
import java.util.List;

public interface Tickable extends IGameObject {

    List<Tickable> active = new ArrayList<>();
    List<Tickable> expended = new ArrayList<>();
    List<Tickable> newborn = new ArrayList<>();

    void tick();
}
