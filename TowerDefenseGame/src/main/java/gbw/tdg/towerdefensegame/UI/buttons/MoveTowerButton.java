package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.TowerFunctionsDisplay;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MoveTowerButton extends Button {

    private static final Point2D textOffset = new Point2D(0,0);
    private final TowerFunctionsDisplay display;

    public MoveTowerButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO, 0,0,new RText(
             "Move",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ));
        display = towerFunctionsDisplay;
    }

    @Override
    public void onInteraction(){
        display.onTowerMove();
    }
}
