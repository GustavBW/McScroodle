package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.TargetingType;
import gbw.tdg.towerdefensegame.tower.Tower;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SetTargetingButton extends Button {

    private final Tower tower;
    private final TargetingType type;

    public SetTargetingButton(Tower tower, TargetingType type) {
        super(Point2D.ZERO,0,0, new RText(
                type.asString,Point2D.ZERO,0, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ),true);
        this.tower = tower;
        this.type = type;
        super.setRimColor(Color.TRANSPARENT);
    }

    @Override
    public void onInteraction(){
        tower.setTargetingType(type);
    }
}
