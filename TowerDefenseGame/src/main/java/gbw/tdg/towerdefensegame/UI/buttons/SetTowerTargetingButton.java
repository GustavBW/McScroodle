package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.TargetingType;
import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class SetTowerTargetingButton extends Button{

    private final TowerFunctionsDisplay display;
    private final GraphicalInventory<Button> menu;
    private boolean menuIsSpawned = true;
    private final List<Button> buttons = new ArrayList<>();

    public SetTowerTargetingButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO,0,0,new RText(
                "Change Targeting",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ),true);
        this.display = towerFunctionsDisplay;
        super.setRimColor(Color.TRANSPARENT);

        for(TargetingType type : TargetingType.values()){
            buttons.add(new SetTargetingButton(display.getTower(),type)
                    .setTextAlignments(0,0.3)
                    .setImage(ContentEngine.BUTTONS.getImage("ArrowedButtonRightFillBlack"))
                    .setTextAlignConstants(15,0)
                    .setShouldRenderBackground(false)
            );
        }

        this.menu = new GraphicalInventory<>(
                1,buttons.size(),position.add(sizeX,0),Point2D.ZERO,
                2,57);

        menu.setBackgroundColor(Color.TRANSPARENT);
        menu.addAll(buttons);
    }

    @Override
    public void onClick(MouseEvent event){
        if(!menuIsSpawned){
            menu.destroy();
        }else{
            menu.spawn();
        }
        menuIsSpawned = !menuIsSpawned;
    }
    @Override
    public void destroy(){
        super.destroy();
        menu.destroy();
        menuIsSpawned = false;
    }

    @Override
    public void spawn(){
        super.spawn();
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
        menu.setDimensions(new Point2D(
                dim.getX() * .7,
                menu.size() * dim.getY()
        ));
    }
    @Override
    public Clickable getRoot(){
        return display.getTower();
    }
}
