package gbw.tdg.towerdefensegame.handlers;

import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

public interface ClickListener {

    Set<ClickListener> active = new HashSet<>();
    Set<ClickListener> expended = new HashSet<>();
    Set<ClickListener> newborn = new HashSet<>();

    void trigger(MouseEvent event);

}
