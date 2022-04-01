package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MouseHandler implements EventHandler<MouseEvent> {

    public static boolean locked, someoneListening, nextClick = false;
    public static Point2D mousePos = new Point2D(0,0);

    public MouseHandler(){

    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Point2D clickPos = new Point2D(mouseEvent.getX(),mouseEvent.getY());

        if(someoneListening){
            nextClick = true;
        }

        if(!locked) {
            for (Clickable c : Clickable.active) {
                if (c.isInBounds(clickPos)) {
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
