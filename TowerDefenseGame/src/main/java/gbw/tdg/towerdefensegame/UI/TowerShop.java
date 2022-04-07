package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.buttons.TowerBuyButton;
import gbw.tdg.towerdefensegame.handlers.ClickListener;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TowerShop implements IClickableOwner, Tickable, ClickListener {
    private Point2D position = new Point2D(0, Main.canvasSize.getY() * 0.76);
    private final double sizeX = Main.canvasSize.getX()*0.6, sizeY = Main.canvasSize.getY() - position.getY();
    private GraphicalInventory<TowerBuyButton> shop;
    private boolean shopLocked = false;
    private int pointBuyPoints = 5, amountBought = 0,  reloadCost = 100;
    private TowerBuyButton selectedTowerOffering;

    public TowerShop(){
        this.shop = new GraphicalInventory<>(3, sizeX, sizeY, 10, position,87D);
        shop.add(getNewOffering());
        shop.add(getNewOffering());
        shop.add(getNewOffering());
    }

    private TowerBuyButton getNewOffering(){
        int cost = pointBuyPoints * 2 + amountBought;
        return new TowerBuyButton(new RText(), new Tower(pointBuyPoints), this, cost);
    }

    @Override
    public void tick(){

    }

    @Override
    public void trigger(MouseEvent event){
        if(selectedTowerOffering != null){
            setShopLock(false);
            Point2D newTowPos = MouseHandler.mousePos.subtract(selectedTowerOffering.getTower().getDimensions().multiply(0.5));
            selectedTowerOffering.getTower().setPosition(newTowPos);
            selectedTowerOffering.getTower().spawn();
            towerBought(selectedTowerOffering);
            selectedTowerOffering = null;

        }
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        ClickListener.newborn.add(this);
        shop.spawn();
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
        ClickListener.expended.add(this);
        shop.destroy();
    }

    private void setShopLock(boolean state){
        shopLocked = !shopLocked;
        if(state){
            lockShop();
        }else{
            unlockShop();
        }
    }

    private void lockShop(){
        for(TowerBuyButton tBB : shop.getAll()){
            tBB.setDisable(true);
        }
    }
    private void unlockShop(){
        for(TowerBuyButton tBB : shop.getAll()){
            tBB.setDisable(false);
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public double getRenderingPriority() {
        return shop.getRenderingPriority();
    }

    public void setPosition(Point2D p) {
        this.position = p;
        shop.setPosition(p);
    }

    public Inventory<TowerBuyButton> getShop(){
        return shop;
    }

    public void increasePointBuy(int amount){
        pointBuyPoints += amount;
    }

    private void towerBought(TowerBuyButton tBB){

        shop.replace(tBB,getNewOffering());
        if(amountBought % 3 == 0){
            increasePointBuy(1);
        }
        amountBought++;
    }

    @Override
    public void childClicked(Clickable child) {
        if(child instanceof TowerBuyButton) {
            setShopLock(true);
            selectedTowerOffering = (TowerBuyButton) child;

        }
    }
}
