package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.buttons.TowerBuyButton;
import gbw.tdg.towerdefensegame.handlers.ClickListener;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class TowerShop implements IClickableOwner, Tickable, ClickListener {
    private final double sizeX = Main.canvasSize.getX() * 0.6, sizeY = Main.canvasSize.getY() * 0.2;
    private final Point2D position = new Point2D((Main.canvasSize.getX() - sizeX) * .5, Main.canvasSize.getY() * 0.8);
    private final GraphicalInventory<TowerBuyButton> shop;
    private boolean shopLocked = false;
    private int pointBuyPoints = 5, amountBought = 0,  reloadCost = 100;
    private TowerBuyButton selectedTowerOffering;

    public TowerShop(){
        this.shop = new GraphicalInventory<>(3, sizeX, sizeY, 10, position,87D);
        shop.addAll(List.of(getNewOffering(),getNewOffering(),getNewOffering()));
    }

    private TowerBuyButton getNewOffering(){
        int cost = pointBuyPoints * 2 + amountBought;
        return new TowerBuyButton(new RText(
                "",Point2D.ZERO,4, Color.WHITE, Font.font("Verdana",Main.canvasSize.getX()*0.01)),
                new Tower(pointBuyPoints), this, cost);
    }

    @Override
    public void tick(){
        if(selectedTowerOffering != null){
            selectedTowerOffering.getTower().setPosition(MouseHandler.mousePos.subtract(selectedTowerOffering.getTower().getDimensions().multiply(0.5)));
        }
    }

    @Override
    public void trigger(MouseEvent event){
        if(selectedTowerOffering != null){
            setShopLock(false);
            Point2D newTowPos = MouseHandler.mousePos.subtract(selectedTowerOffering.getTower().getDimensions().multiply(0.5));
            selectedTowerOffering.getTower().setPosition(newTowPos);
            selectedTowerOffering.getTower().setActive(true);
            towerBought(selectedTowerOffering);
            selectedTowerOffering = null;
        }
        ClickListener.expended.add(this);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        shop.spawn();
    }
    @Override
    public void destroy() {
        Tickable.expended.add(this);
        shop.destroy();
    }
    private void setShopLock(boolean state){
        shopLocked = state;
        for(TowerBuyButton tBB : shop.getAll()){
            tBB.setDisable(state);
        }
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
    public void onChildPress(Clickable child, MouseEvent event) {
        if(child instanceof TowerBuyButton) {
            setShopLock(true);
            selectedTowerOffering = (TowerBuyButton) child;
            Tower tower = selectedTowerOffering.getTower();
            tower.setActive(false);
            ClickListener.newborn.add(this);
            tower.spawn();
            tower.onClick(event);
        }
    }

    @Override
    public void onChildClick(Clickable child, MouseEvent event) {

    }

    @Override
    public void onChildRelease(Clickable child, MouseEvent event) {

    }
}
