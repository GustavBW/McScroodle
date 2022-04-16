package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;

public class StartGameButton extends Button{

    public StartGameButton(Point2D position, double sizeX, double sizeY, RText textUnit) {
        super(position, sizeX, sizeY, textUnit);
    }
    @Override
    public void onInteraction(){
        Main.onGameStart();
    }
}
