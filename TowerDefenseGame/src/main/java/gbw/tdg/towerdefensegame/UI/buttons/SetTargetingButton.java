package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.TargetingType;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.tower.Tower;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SetTargetingButton extends Button {

    private final Tower tower;
    private final TargetingType type;

    public SetTargetingButton(Tower tower, TargetingType type) {
        super(Point2D.ZERO,0,0, new RText(
                type.asString,Point2D.ZERO,0, Color.CYAN, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ),true);
        super.getText().setDropShadowColor(Color.DARKGREY);
        this.tower = tower;
        this.type = type;
        super.setRimColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(MouseEvent event){
        tower.setTargetingType(type);
    }

    @Override
    public Clickable getRoot(){
        return tower;
    }
}
