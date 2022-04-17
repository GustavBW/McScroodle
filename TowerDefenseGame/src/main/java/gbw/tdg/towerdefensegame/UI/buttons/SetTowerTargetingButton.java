package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.TargetingType;
import gbw.tdg.towerdefensegame.TowerFunctionsDisplay;
import gbw.tdg.towerdefensegame.UI.GraphicalInventory;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class SetTowerTargetingButton extends Button{

    private final TowerFunctionsDisplay display;
    private final GraphicalInventory<Button> menu;
    private final List<Button> buttons = new ArrayList<>();

    public SetTowerTargetingButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO,0,0,new RText(
                "Change Targeting",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ));
        this.display = towerFunctionsDisplay;

        for(TargetingType type : TargetingType.values()){
            buttons.add(new SetTargetingButton(display.getTower(),type));
        }

        this.menu = new GraphicalInventory<>(1,sizeX,Main.canvasSize.getY()*0.15,2,position.add(sizeX,0),56,buttons.size());
        menu.addAll(buttons);
    }

    @Override
    public void onInteraction(){
        menu.spawn();
    }

    @Override
    public void deselect(){
        menu.destroy();
    }

    @Override
    public void setPosition(Point2D p){
        super.setPosition(p);
        menu.setPosition(p.add(sizeX,0));
    }

    @Override
    public void setDimensions(Point2D dim){
        super.setDimensions(dim);
        menu.setDimensions(new Point2D(dim.getX(), Main.canvasSize.getY()*0.15));
    }
}
