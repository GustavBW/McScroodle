package gbw.roguelike.handlers;

import gbw.roguelike.Main;
import gbw.roguelike.interfaces.Interactible;
import javafx.geometry.Point2D;

public class InteractionHandler {


    public InteractionHandler(){

    }


    public static void evaluateInteractibles(){

        for(Interactible i : Interactible.interactibles){
            if(i.isWithinRange(new Point2D(Main.canvasDim.getX() * 0.5, Main.canvasDim.getY() * 0.5))){
                i.onInteraction();
            }
        }

    }

}
