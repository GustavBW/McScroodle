package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.buttons.AugmentBuyButton;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.handlers.ClickListener;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import gbw.tdg.towerdefensegame.tower.ITower;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AugmentShop implements IClickableOwner,Tickable, ClickListener {

    private final GraphicalInventory<AugmentBuyButton> shop;
    private final GraphicalInventory<AugmentBuyButton> storedAugs;
    private final double sizeX = Main.canvasSize.getX()*0.15, sizeY = Main.canvasSize.getY() * 0.3;
    private Point2D position = new Point2D(Main.canvasSize.getX() - sizeX, Main.canvasSize.getY() * 0.1);
    private boolean shopLocked = false;
    private int baseCost = 5, amountBought = 0,  reloadCost = 100;
    private AugmentBuyButton selectedOffering;

    public AugmentShop(){
        this.shop = new GraphicalInventory<>(1, sizeX, sizeY, 10, position,87D,3);
        shop.addAll(List.of(getNewOffering(),getNewOffering(),getNewOffering()));
        double margin = Main.canvasSize.getY() * 0.01;
        this.storedAugs = new GraphicalInventory<>(1,sizeX * 0.9,sizeY,10,position.add(0,sizeY + margin),87D,6);
    }

    private AugmentBuyButton getNewOffering(){
        int cost = baseCost * 2 + amountBought;
        return new AugmentBuyButton(new RText(
                "",Point2D.ZERO,4, Color.WHITE, Font.font("Impact",Main.canvasSize.getX()*0.01)),
                Augment.getRandom(10), this, cost);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        shop.spawn();
        storedAugs.spawn();
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
        shop.destroy();
        storedAugs.destroy();
    }

    @Override
    public void tick() {
        if(selectedOffering != null){
            selectedOffering.getIcon().setPosition(MouseHandler.mousePos);
        }
    }

    @Override
    public void trigger(MouseEvent event) {
        if(selectedOffering != null){
            Tower tFound = findTowerOnPos(new Point2D(event.getX(),event.getY()));
            selectedOffering.getIcon().destroy();
            boolean success = false;

            if(tFound != null){
                if(tFound.addAugment(selectedOffering.getAugment())){
                    success = true;
                }
            }

            if(!success){
                storedAugs.add(new AugmentBuyButton(selectedOffering, false,true,true));
            }

            setShopLock(false);
            augmentBought(selectedOffering);
            selectedOffering = null;
        }
        ClickListener.expended.add(this);
    }
    private Tower findTowerOnPos(Point2D p){
        for(ITower t : ITower.active){
            if(t.isInBounds(p)){
                return (Tower) t;
            }
        }
        return null;
    }

    @Override
    public void childClicked(Clickable child) {
        if(child instanceof AugmentBuyButton) {
            setShopLock(true);
            selectedOffering = (AugmentBuyButton) child;
            selectedOffering.destroy();
            selectedOffering.getIcon().setDimensions(Main.canvasSize.multiply(0.03));
            selectedOffering.getIcon().spawn();
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
        storedAugs.remove(aBB);

        if(amountBought % 3 == 0){
            increaseBaseCost(1);
        }
        amountBought++;
    }
    public void increaseBaseCost(int amount){
        baseCost += amount;
    }
}
