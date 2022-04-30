package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.buttons.AugmentBuyButton;
import gbw.tdg.towerdefensegame.UI.buttons.BounceBackButton;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.handlers.ClickListener;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import gbw.tdg.towerdefensegame.tower.ITower;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AugmentShop implements IClickableOwner,Tickable, ClickListener {

    private final GraphicalInventory<AugmentBuyButton> shop;
    private final DragnDropInventory<BounceBackButton<Augment>> storedAugs;
    private final double sizeX = Main.canvasSize.getX()*0.15, sizeY = Main.canvasSize.getY() * 0.3;
    private Point2D position = new Point2D(Main.canvasSize.getX() - sizeX, Main.canvasSize.getY() * 0.1);
    private boolean shopLocked = false;
    private int baseCost = 5, amountBought = 0,  reloadCost = 100;
    private AugmentBuyButton selectedOffering;

    public AugmentShop(){
        this.shop = new GraphicalInventory<>(1, sizeX, sizeY, 10, position,87D,3);
        shop.addAll(List.of(getNewOffering(),getNewOffering(),getNewOffering()));
        double margin = Main.canvasSize.getY() * 0.01;

        this.storedAugs = new DragnDropInventory<>(6,1, sizeX * 0.9, sizeY, 10, position.add(0, sizeY + margin), 87D) {
            @Override
            public void whilestObjHeld() {
                System.out.println("DnDGI: Updating position of " + getSelected());
                this.getSelected().setPosition(MouseHandler.mousePos);
            }
            @Override
            public void onChildPress(Clickable child, MouseEvent event) {
                System.out.println("DnDGIO: Child clicked is " + child);
                this.setSelected((BounceBackButton<Augment>) child);
                this.setDragOnGoing(true);
            }
            @Override
            public void onChildRelease(Clickable child, MouseEvent event) {
                this.setDragOnGoing(false);
                Tower t = ITower.getOnPos(MouseHandler.mousePos);
                boolean success = false;

                System.out.println("DnDGI: Child release. Tower is " + t);

                if(t != null){
                    success = t.addAugment(getSelected().getAssociatedObj());
                }

                System.out.println("DnDGI: Success: " + success);

                this.remove(getSelected());
                getSelected().destroy();

                if(!success){
                    this.add(getSelected());
                }

                this.setSelected(null);
            }
        };

    }

    private AugmentBuyButton getNewOffering(){
        int cost = baseCost * 2 + amountBought;
        return new AugmentBuyButton(Point2D.ZERO, 400,400, new RText(
                "",Point2D.ZERO,4, Color.WHITE, Font.font("Impact",Main.canvasSize.getX()*0.01)),
                Augment.getRandom(cost), this, cost);
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
            Tower tFound = ITower.getOnPos(new Point2D(event.getX(),event.getY()));
            boolean success = false;

            if(tFound != null){
                success = tFound.addAugment(selectedOffering.getAugment());
            }

            setShopLock(false);
            augmentBought(selectedOffering);

            if(!success){
                BounceBackButton<Augment> asBounce = new BounceBackButton<>(
                        Point2D.ZERO, Point2D.ZERO ,RText.EMPTY, storedAugs, selectedOffering.getAugment()
                );
                asBounce.setImage(selectedOffering.getAugment().getImage());

                storedAugs.add(asBounce);
            }

            selectedOffering.getIcon().destroy();
            selectedOffering = null;
        }
        ClickListener.expended.add(this);
    }

    @Override
    public void onChildPress(Clickable child, MouseEvent event) {
        if(child instanceof AugmentBuyButton) {
            setShopLock(true);
            selectedOffering = (AugmentBuyButton) child;
            selectedOffering.destroy();
            selectedOffering.getIcon().setDimensions(Main.canvasSize.multiply(0.03));
            selectedOffering.getIcon().spawn();
            ClickListener.newborn.add(this);
        }
    }

    @Override
    public void onChildClick(Clickable child, MouseEvent event) {

    }

    @Override
    public void onChildRelease(Clickable child, MouseEvent event) {

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
