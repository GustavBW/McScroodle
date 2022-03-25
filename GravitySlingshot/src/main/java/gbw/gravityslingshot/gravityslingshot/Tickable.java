package gbw.gravityslingshot.gravityslingshot;

import java.util.ArrayList;
import java.util.List;

public interface Tickable {

    List<Tickable> active = new ArrayList<>();
    List<Tickable> expended = new ArrayList<>();
    List<Tickable> newborn = new ArrayList<>();

    public void tick();
}
