package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public abstract class TickButton<T> extends Button implements Tickable {

    private T associatedValue;
    private int updateDelayMS = 1000;
    private long lastcall;

    public TickButton(Point2D position, double sizeX, double sizeY, RText textUnit, Clickable root, T t, boolean shouldRenderBackground) {
        super(position, sizeX, sizeY, textUnit, root, shouldRenderBackground);
        associatedValue = t;
    }
    public TickButton(Image image, Point2D position, Point2D dim, RText textUnit, Clickable root){
        super(image, position, dim, textUnit, root);
    }

    public TickButton(Point2D position, double sizeX, double sizeY, RText textUnit, boolean shouldRenderBackground) {
        super(position, sizeX, sizeY, textUnit, shouldRenderBackground);
    }

    public TickButton(Point2D position, double sizeX, double sizeY) {
        super(position, sizeX, sizeY);
    }

    public TickButton(Point2D position, double sizeX, double sizeY, RText textUnit) {
        super(position, sizeX, sizeY, textUnit);
    }

    public T getAssociatedValue(){
        return associatedValue;
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis() >= updateDelayMS + lastcall) {
            update();
            lastcall = System.currentTimeMillis();
        }
    }

    public abstract void update();

    public TickButton<T> setUpdateDelay(int ms){
        this.updateDelayMS = ms;
        return this;
    }

    public TickButton<T> setImage2(Image image){
        super.setImage(image);
        return this;
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
}
