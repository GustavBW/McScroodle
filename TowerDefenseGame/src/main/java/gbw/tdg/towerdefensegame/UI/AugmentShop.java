package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.buttons.AugmentBuyButton;
import gbw.tdg.towerdefensegame.UI.buttons.TowerBuyButton;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.handlers.ClickListener;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AugmentShop implements IClickableOwner,Tickable, ClickListener {

    private final GraphicalInventory<AugmentBuyButton> shop;
    private final double sizeX = Main.canvasSize.getX()*0.15, sizeY = Main.canvasSize.getY() * 0.3;
    private Point2D position = new Point2D(Main.canvasSize.getX() - sizeX, Main.canvasSize.getY() * 0.1);
    private boolean shopLocked = false;
    private int pointBuyPoints = 5, amountBought = 0,  reloadCost = 100;
    private AugmentBuyButton selectedOffering;

    public AugmentShop(){
        this.shop = new GraphicalInventory<>(1, sizeX, sizeY, 10, position,87D,3);
        shop.addAll(List.of(getNewOffering(),getNewOffering(),getNewOffering()));
    }

    private AugmentBuyButton getNewOffering(){
        int cost = pointBuyPoints * 2 + amountBought;
        return new AugmentBuyButton(new RText(
                "",Point2D.ZERO,4, Color.WHITE, Font.font("Impact",Main.canvasSize.getX()*0.01)),
                Augment.getRandom(10), this, cost);
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

    @Override
    public void tick() {

    }

    @Override
    public void trigger(MouseEvent event) {
        if(selectedOffering != null){
            setShopLock(false);
            augmentBought(selectedOffering);
            selectedOffering = null;
        }
        ClickListener.expended.add(this);
    }

    @Override
    public void childClicked(Clickable child) {
        if(child instanceof AugmentBuyButton) {
            setShopLock(true);
            selectedOffering = (AugmentBuyButton) child;
            ClickListener.newborn.add(this);
        }
    }

    private void setShopLock(boolean state){
        shopLocked = state;
        for(AugmentBuyButton aBB : shop.getAll()){
            aBB.setDisable(state);
        }
    }

    private void augmentBought(AugmentBuyButton aBB){

        shop.replace(aBB,getNewOffering());
        if(amountBought % 3 == 0){
            increasePointBuy(1);
        }
        amountBought++;
    }
    public void increasePointBuy(int amount){
        pointBuyPoints += amount;
    }
}
