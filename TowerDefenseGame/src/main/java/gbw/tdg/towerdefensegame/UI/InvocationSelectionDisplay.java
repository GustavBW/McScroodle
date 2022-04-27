package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import gbw.tdg.towerdefensegame.tower.StatType;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class InvocationSelectionDisplay extends Button {

    private final List<Invocation> invocations;
    private final GraphicalInventory<SmallInvocationDisplay> contents;
    private final TowerStatDisplay display;
    private final Button spawningButton;

    public InvocationSelectionDisplay(StatType t, Tower tower, TowerStatDisplay display, Button spawningButton) {
        super(
                Main.canvasSize.multiply(0.25),
                Main.canvasSize.getX() * 0.5,
                Main.canvasSize.getY() * 0.35,
                RText.EMPTY,
                tower,
                true
        );
        this.display = display;
        this.spawningButton = spawningButton;
        invocations = Invocation.getForStatType(t,3);
        contents = new GraphicalInventory<>(3,1,new Point2D(sizeX,sizeY),position,15 * Main.scale.getX(),renderingPriority);

        for(Invocation i : invocations){
            contents.add(new SmallInvocationDisplay(Point2D.ZERO,new Point2D(sizeX/3,sizeY),tower,true,i) {
                @Override
                public void onClick(MouseEvent event) {
                    System.out.println("InvocationSelectionDisplay: You chose " + getInvo().getName());
                    InvocationSelectionDisplay.this.getDisplay().onInvocationSelected(getInvo(), InvocationSelectionDisplay.this.getSpawningButton());
                    InvocationSelectionDisplay.this.destroy();
                }
            });
        }
    }
    public TowerStatDisplay getDisplay(){
        return display;
    }
    public Button getSpawningButton(){
        return spawningButton;
    }

    @Override
    public void spawn(){
        super.spawn();
        contents.spawn();
    }

    @Override
    public void destroy(){
        super.destroy();
        contents.destroy();
    }

    @Override
    public void deselect(){
        this.destroy();
    }

}