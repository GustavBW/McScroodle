package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.buttons.BounceBackButton;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class DragnDropInventory<T extends BounceBackButton> extends GraphicalInventory<T> implements IClickableOwner, Tickable {

    private T selected;

    public DragnDropInventory( int rows, int coloumns, double width, double height, double margin, Point2D position, double renderingPrio) {
        super(coloumns, width, height, margin, position, renderingPrio, rows);
    }

    @Override
    public void tick() {
        if(selected != null){
            selected.setPosition(MouseHandler.mousePos);
        }
    }
    @Override
    public void spawn(){
        super.spawn();
        Tickable.newborn.add(this);
    }
    @Override
    public void destroy(){
        super.destroy();
        Tickable.expended.add(this);
    }

    @Override
    protected void addObject(T obj, int slot){
        super.addObject(obj,slot);
        obj.setOwner(this);
    }


    @Override
    public void onChildPress(Clickable child, MouseEvent event) {
        selected = (T) child;
    }

    @Override
    public void onChildRelease(Clickable child, MouseEvent event) {
        remove((T) child);
        child.spawn();
        selected = null;
    }


    @Override
    public void onChildClick(Clickable child, MouseEvent event) {

    }


}
