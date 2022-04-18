package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.Collidable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MouseHandler implements EventHandler<MouseEvent> {

    public static boolean locked;
    private boolean foundClickable;
    public static Point2D mousePos = new Point2D(0,0);
    private Clickable selected;
    private static final List<Clickable> storedDuringLock = new ArrayList<>();

    public MouseHandler(){

    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        foundClickable = false;
        Point2D clickPos = new Point2D(mouseEvent.getX(),mouseEvent.getY());

        for(ClickListener cL : ClickListener.active){
            cL.trigger(mouseEvent);
            //System.out.println("ClickListener: " + cL);
        }

        for (Clickable c : Clickable.active) {
            if (c.isInBounds(clickPos)) {
                if(selected != null && selected != c.getRoot()){
                    System.out.println("selected is: " + selected + " clicked is: " + c);
                    selected.deselect();
                }
                selected = c;
                foundClickable = true;
                c.onInteraction();
                //System.out.println("You clicked on " + c);
                break;
            }
        }

        if(selected != null && !foundClickable){
            selected.deselect();
        }


        cleanUp();
    }

    private static void cleanUp(){
        ClickListener.active.addAll(ClickListener.newborn);
        ClickListener.active.removeAll(ClickListener.expended);
        ClickListener.newborn.clear();
        ClickListener.expended.clear();
    }

    public void updateMousePos(MouseEvent mEvent){
        mousePos = new Point2D(mEvent.getX(),mEvent.getY());
    }

    public static void setLocked(boolean locked){
        if(locked) {
            cleanUp();
            storedDuringLock.addAll(Clickable.active);
            Clickable.active.clear();
        }else{
            Clickable.newborn.addAll(storedDuringLock);
            storedDuringLock.clear();
            cleanUp();
        }
    }

}
