package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.buttons.TowerBuyButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TowerShop implements Renderable, IClickableOwner, Tickable {
    private final static double renderingPriority = 90D;
    private final Point2D position = new Point2D(0, Main.canvasSize.getY() * 0.76);
    private final double sizeX = Main.canvasSize.getX()*0.6, sizeY = Main.canvasSize.getY() - position.getY();
    private double tBBWidth = sizeX * 0.3;
    private final Color backgroundColor = new Color(0,0,0,0.5);
    private List<TowerBuyButton> currentOfferings;
    private boolean shopLocked = false;
    private int pointBuyPoints = 5, amountBought = 0,  reloadCost = 100, buyPhase = 0;
    private TowerBuyButton selectedTowerOffering;
    private Map<Integer, Point2D> slots;

    public TowerShop(){
        this.slots = new HashMap<>();
        this.currentOfferings = getOfferings();

    }

    private List<TowerBuyButton> getOfferings() {
        Point2D slot1 = position.add(10,10);
        Point2D slot2 = slot1.add(10 + tBBWidth,0);
        Point2D slot3 = slot2.add(10 + tBBWidth,0);
        int cost = pointBuyPoints * 2;

        List<TowerBuyButton> offerings = new ArrayList<>(List.of(
                new TowerBuyButton(slot1, tBBWidth, sizeY * 0.8, new RText(slot1.add(30, 20)), new Tower(pointBuyPoints), this, cost),
                new TowerBuyButton(slot2, tBBWidth, sizeY * 0.8, new RText(slot2.add(30, 20)), new Tower(pointBuyPoints), this, cost),
                new TowerBuyButton(slot3, tBBWidth, sizeY * 0.8, new RText(slot3.add(30, 20)), new Tower(pointBuyPoints), this, cost)
        ));
        slots.put(0,slot1);
        slots.put(1,slot2);
        slots.put(2,slot3);

        return offerings;
    }

    @Override
    public void tick(){
        if(selectedTowerOffering != null){
            if(MouseHandler.nextClick){
                setShopLock(false);
                //MouseHandler.toggleLock();
                selectedTowerOffering.getTower().setPosition(MouseHandler.mousePos);
                selectedTowerOffering.getTower().spawn();
                towerBought();

                selectedTowerOffering.destroy();
                currentOfferings.remove(selectedTowerOffering);

                MouseHandler.someoneListening = false;
                MouseHandler.nextClick = false;
                selectedTowerOffering = null;
            }
        }
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
        for(TowerBuyButton tBB : currentOfferings){
            tBB.spawn();
        }
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
        for(TowerBuyButton tBB : currentOfferings){
            tBB.destroy();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(), position.getY(), sizeX,sizeY);
        for(TowerBuyButton tBB : currentOfferings){
            tBB.render(gc);
        }
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
        for(TowerBuyButton tBB : currentOfferings){
            tBB.setDisabled(true);
        }
    }
    private void unlockShop(){
        for(TowerBuyButton tBB : currentOfferings){
            tBB.setDisabled(false);
        }
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    public void increasePointBuy(int amount){
        pointBuyPoints += amount;
    }

    private void towerBought(){
        int i;
        for(i = 0; i < currentOfferings.size(); i++){
            if(currentOfferings.get(i) == selectedTowerOffering){
                break;
            }
        }
        if(amountBought % 3 == 0) {
            increasePointBuy(1);
        }
        fillNewOffering(i);
        amountBought++;
    }

    private void fillNewOffering(int index){
        int cost = pointBuyPoints * 2;
        currentOfferings.add(index, new TowerBuyButton(
                slots.get(index), tBBWidth, sizeY * 0.8, new RText(slots.get(index).add(30, 20)), new Tower(pointBuyPoints), this, cost
        ));
    }

    @Override
    public void childClicked(Clickable child) {
        if(child instanceof TowerBuyButton) {

            setShopLock(true);
            selectedTowerOffering = (TowerBuyButton) child;
            MouseHandler.someoneListening = true;

        }
        System.out.println("Child clicked " + child);
    }
}
