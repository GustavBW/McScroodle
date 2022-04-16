package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.TowerFunctionsDisplay;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SetTowerTargetingButton extends Button{

    private final TowerFunctionsDisplay display;

    public SetTowerTargetingButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO,0,0,new RText(
                "Change Targeting",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ));
        this.display = towerFunctionsDisplay;
    }

    @Override
    public void onInteraction(){
        display.showTargetingOptions();
    }
}
