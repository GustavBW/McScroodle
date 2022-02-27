package gbw.roguelike.handlers;

import gbw.roguelike.interfaces.Clickable;
import gbw.roguelike.interfaces.iGameObject;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class ClickableManager {

    public static ArrayList<iGameObject> toRemove = new ArrayList<>();
    public static ArrayList<Clickable> toAdd = new ArrayList<>();

    public static void evaluate(Point2D mPos){

        for(Clickable c : Clickable.clickables){

            if(c.isInBounds(mPos)){
                c.onClicked();
                break;
            }
        }

        cleanUp();
    }

    private static void cleanUp(){

    }

}
