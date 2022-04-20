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
    public static Point2D mousePos = new Point2D(0,0);
    private Clickable selected, previous, selectedRoot,previousRoot;
    private static final List<Clickable> storedDuringLock = new ArrayList<>();

    public MouseHandler(){

    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Point2D clickPos = new Point2D(mouseEvent.getX(),mouseEvent.getY());

        for(ClickListener cL : ClickListener.active){
            cL.trigger(mouseEvent);
            //System.out.println("ClickListener: " + cL);
        }

        previous = selected;
        previousRoot = selectedRoot;
        selected = null;
        selectedRoot = null;

        for (Clickable c : Clickable.active) {
            if (c.isInBounds(clickPos)) {
                selected = c;
                selectedRoot = c.getRoot();
                System.out.println("MouseHandler: Clicked " + c);
                break;
            }
        }

        System.out.println("MouseHandler: selected: " + selected + " previous: " + previous);

        if(selected != null){
            selected.onInteraction();
        }
        if (previous != null && !rootsAreEqual(selected, previous)) {
            System.out.println("MouseHandler: Deselecting " + previous);
            previous.deselect();
        }

        System.out.println("#");

        cleanUp();
    }

    private boolean rootsAreEqual(Clickable first, Clickable second){
        if(first == null || second == null){
            return false;
        }
        System.out.println("MouseHandler roots: " + first.getRoot()  + " | " + second.getRoot());

        if(first.getRoot() == null || second.getRoot() == null){
            return false;
        }

        return first.getRoot() == second.getRoot();
    }

    private static void cleanUp(){
        ClickListener.active.addAll(ClickListener.newborn);
        ClickListener.active.removeAll(ClickListener.expended);
        ClickListener.newborn.clear();
        ClickListener.expended.clear();

        Clickable.active.addAll(Clickable.newborn);
        Clickable.active.removeAll(Clickable.expended);
        Clickable.expended.clear();
        Clickable.newborn.clear();
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
