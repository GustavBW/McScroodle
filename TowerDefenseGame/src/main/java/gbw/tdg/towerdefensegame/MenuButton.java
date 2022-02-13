package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;

public class MenuButton extends Button{


    public MenuButton(Point2D position, double sizeX, double sizeY, RText textUnit) {
        super(position, sizeX, sizeY, textUnit);
    }

    @Override
    public void onInteraction(){
        System.out.println("You clicked the MenuButton");
    }
}
