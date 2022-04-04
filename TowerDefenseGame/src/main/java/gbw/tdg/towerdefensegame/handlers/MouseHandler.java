package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.UI.Clickable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MouseHandler implements EventHandler<MouseEvent> {

    public static boolean locked;
    public static Point2D mousePos = new Point2D(0,0);
    private Clickable selected;

    public MouseHandler(){

    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Point2D clickPos = new Point2D(mouseEvent.getX(),mouseEvent.getY());

        for(ClickListener cL : ClickListener.active){
            cL.trigger(mouseEvent);
        }

        if(!locked) {
            for (Clickable c : Clickable.active) {
                if (c.isInBounds(clickPos)) {
                    if(selected != null && selected != c){
                        selected.deselect();
                    }
                    selected = c;
                    c.onInteraction();
                    break;
                }
            }
        }
    }

    public void updateMousePos(MouseEvent mEvent){
        mousePos = new Point2D(mEvent.getX(),mEvent.getY());
    }

    public static void lock(){
        locked = true;
    }
    public static boolean toggleLock(){
        locked = !locked;
        return locked;
    }

}
