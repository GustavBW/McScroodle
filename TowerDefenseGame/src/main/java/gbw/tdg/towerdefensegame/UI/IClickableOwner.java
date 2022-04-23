package gbw.tdg.towerdefensegame.UI;

import javafx.scene.input.MouseEvent;

public interface IClickableOwner {

    void onChildPress(Clickable child, MouseEvent event);
    void onChildClick(Clickable child, MouseEvent event);
    void onChildRelease(Clickable child, MouseEvent event);

}
