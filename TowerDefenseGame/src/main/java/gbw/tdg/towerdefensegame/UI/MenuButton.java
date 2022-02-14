package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import javafx.geometry.Point2D;

public class MenuButton extends Button{


    public MenuButton(Point2D position, double sizeX, double sizeY, RText textUnit) {
        super(position, sizeX, sizeY, textUnit);
    }

    @Override
    public void onInteraction(){
        Main.setState(GameState.START_MENU);
    }
}
