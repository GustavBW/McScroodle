package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MouseHandler implements EventHandler<MouseEvent> {

    private ArrayList<Clickable> clickables;

    public MouseHandler(ArrayList<Clickable> list){
        this.clickables = list;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Point2D clickPos = new Point2D(mouseEvent.getX(),mouseEvent.getY());

        for(Clickable c : clickables){
            if(c.isInBounds(clickPos)){
                c.onInteraction();
                break;
            }
        }

    }
}
