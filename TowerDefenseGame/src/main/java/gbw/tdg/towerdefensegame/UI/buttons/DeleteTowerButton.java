package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.TowerFunctionsDisplay;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DeleteTowerButton extends Button {

    private final static Point2D textOffset = new Point2D(0,0);
    private final TowerFunctionsDisplay display;

    public DeleteTowerButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO, 0,0,new RText(
                "Sell (" + towerFunctionsDisplay.getTower().getWorth() + " G)",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ),true);
        this.display = towerFunctionsDisplay;
        super.setRimColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(MouseEvent event){
        display.onTowerDelete();
    }

    @Override
    public Clickable getRoot(){
        return display.getTower();
    }
}
