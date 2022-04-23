package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.UI.Clickable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MouseHandler {

    public static boolean locked;
    public static Point2D mousePos = new Point2D(0,0);
    private Clickable selected, previous, selectedRoot,previousRoot;
    private static final List<Clickable> storedDuringLock = new ArrayList<>();

    public MouseHandler(){}

    public void onClick(MouseEvent mouseEvent) {
        for(ClickListener cL : ClickListener.active){
            cL.trigger(mouseEvent);
            //System.out.println("ClickListener: " + cL);
        }
        Clickable found = search(posOf(mouseEvent));
        updateStoredValues(found);
        if(selected != null){
            selected.onClick(mouseEvent);
        }
        if (previousRoot != null && !rootsAreEqual(selected, previous)) {
            previousRoot.deselect();
        }
    }
    public void onPress(MouseEvent e) {
        Clickable found = search(posOf(e));
        updateStoredValues(found);
        if(selected != null){
            selected.onPress(e);
        }
        if (previousRoot != null && !rootsAreEqual(selected, previous)) {
            previousRoot.deselect();
        }
    }

    public void onRelease(MouseEvent e) {
        if(selected != null){
            selected.onRelease(e);
        }
    }
    private Point2D posOf(MouseEvent e){
        return new Point2D(e.getX(),e.getY());
    }
    private void updateStoredValues(Clickable found){
        previous = selected;
        previousRoot = selectedRoot;
        selected = found;
        selectedRoot = found == null ? null : found.getRoot();
    }
    private Clickable search(Point2D clickPos){
        cleanUp();
        for (Clickable c : Clickable.active) {
            if (c.isInBounds(clickPos)) {
                return c;
            }
        }
        return null;
    }
    private boolean rootsAreEqual(Clickable first, Clickable second){
        if(first == null || second == null){
            return false;
        }

        if(first.getRoot() == null || second.getRoot() == null){
            return false;
        }

        return first.getRoot().equals(second.getRoot());
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
