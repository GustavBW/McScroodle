package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.buttons.BounceBackButton;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public abstract class DragnDropInventory<T extends BounceBackButton<?>> extends GraphicalInventory<T> implements IClickableOwner, Tickable {

    private T selected;
    private boolean dragOnGoing;

    public DragnDropInventory( int rows, int coloumns, double width, double height, double margin, Point2D position, double renderingPrio) {
        super(coloumns, width, height, margin, position, renderingPrio, rows);
    }

    public T getSelected(){
        return selected;
    }
    public void setSelected(T obj){
        this.selected = obj;
    }
    public void setDragOnGoing(boolean state){
        this.dragOnGoing = state;
    }

    @Override
    public void tick() {
        if(dragOnGoing){
            whilestObjHeld();
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

    public abstract void whilestObjHeld();

    @Override
    public void onChildClick(Clickable child, MouseEvent event) {

    }


}
