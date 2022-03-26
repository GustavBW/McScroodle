package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.List;

public class MouseInputHandler {

    public static List<MouseEvent> currentlyActive = new LinkedList<>();
    public static Point2D mousePosition = new Point2D(0,0);

    public MouseInputHandler(){

    }


    public void buttonPress(MouseEvent e){
        currentlyActive.add(e);
    }
    public void buttonReleased(MouseEvent e){
        currentlyActive.remove(e);
    }


    public void mouseMoved(MouseEvent e){
        mousePosition = new Point2D(e.getX(),e.getY());
    }

    public static String mousePosToString(){
        return mousePosition.getX() + " | " + mousePosition.getY();
    }
}
