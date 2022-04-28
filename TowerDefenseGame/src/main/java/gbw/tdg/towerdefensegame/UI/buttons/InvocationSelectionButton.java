package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.tower.StatType;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class InvocationSelectionButton extends Button {

    private final StatType type;
    private final InvocationSelectionDisplay display;
    private final TowerStatDisplay statDisplay;
    private boolean toggle = false;
    private Image image;

    public InvocationSelectionButton(StatType t, Tower tower, TowerStatDisplay statDisplay) {
        super(Point2D.ZERO,0,0, RText.EMPTY,tower,true);
        this.type = t;
        this.display = new InvocationSelectionDisplay(t,tower, statDisplay, this);
        this.statDisplay = statDisplay;
        this.image = ContentEngine.INVOCATIONS.getImage("invocationsAvailable");
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(image, position.getX(), position.getY(),sizeX,sizeY);
    }

    @Override
    public void onClick(MouseEvent event){
        if(toggle){
            display.destroy();
        }else {
            display.spawn();
        }

        toggle = !toggle;
    }

    @Override
    public void destroy(){
        super.destroy();
        display.destroy();
        toggle = false;
    }

    @Override
    public void setDimensions(Point2D dim) {

        if(dim.getX() < dim.getY()) {
            dim = new Point2D(dim.getX(), dim.getX());
        }else if(dim.getY() < dim.getX()){
            dim = new Point2D(dim.getY(), dim.getY());
        }

        this.sizeX = dim.getX();
        this.sizeY = dim.getY();
    }

}
