package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.InvocationSelectionButton;
import gbw.tdg.towerdefensegame.UI.buttons.OpenDisplayableDisplayButton;
import gbw.tdg.towerdefensegame.UI.buttons.TickButton;
import gbw.tdg.towerdefensegame.UI.scenes.InGameScreen;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import gbw.tdg.towerdefensegame.tower.StatType;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;

public class TowerStatDisplay extends Button implements Renderable, Tickable, Clickable {

    private double renderingPriority = 57;
    private final RText text;
    private final Tower tower;
    private final Color background = new Color(0,0,0,0.5);
    private final Point2D position;
    private final double someArcNumber = 15 * Main.scale.getX();
    private final GraphicalInventory<Button> augmentDisplay;
    private final GraphicalInventory<Button> upgradeButtons;
    private final Button upgradeCostText;
    private final TowerDisplay display;

    public TowerStatDisplay(Tower t, Point2D position, TowerDisplay display){
        super(position, Main.canvasSize.getX() * 0.1 - ((Main.canvasSize.getY() * 0.1) / 3),Main.canvasSize.getY() * 0.1);
        this.text = new RText(t.getStats(),
                position.add(Main.canvasSize.getX() * 0.015,Main.canvasSize.getX() * 0.007),
                1, Color.WHITESMOKE, Font.font("Impact", Main.canvasSize.getX() * 0.0104));
        this.tower = t;
        this.position = position;
        this.display = display;

        this.augmentDisplay = new GraphicalInventory<>(1,sizeY / 3,sizeY,0,position.add(sizeX,0),renderingPriority,3);
        this.upgradeButtons = new GraphicalInventory<>(3,1,new Point2D(position.getX(),position.getY() + sizeY),new Point2D(sizeX, sizeY * 0.5),0,renderingPriority);

        Font upgradeCostTextFont = Font.font("Impact", 30 * Main.scale.getX());
        this.upgradeCostText = new TickButton<>(
                position.add(0,sizeY + upgradeButtons.getDimensions().getY()), sizeX,sizeY/5,
                new RText("Cost: ",Point2D.ZERO,3, new Color(255 / 255D,200/255D,100/255D,1),upgradeCostTextFont),
                tower,null,false)
        {
            @Override
            public void update() {
                String toWrite = "Cost: "  + Decimals.toXDecimalPlaces(tower.getUpgradeCost(),1) + "G";
                if(tower.getInvoCount() >= 3){
                    toWrite = "Worth: " + Decimals.toXDecimalPlaces(tower.getWorth(),1) + "G";
                }
                this.text.setText(toWrite);
                super.alignText(text);
            }
        }.setUpdateDelay(200).setImage2(ContentEngine.BUTTONS.getImage("BottomBeveledUpgradeFill"));
        this.upgradeCostText.setTextAlignments(0.5,0.3);

        upgradeButtons.addAll(List.of(
                new TickButton<>(Point2D.ZERO,0,0,RText.EMPTY,tower,StatType.DAMAGE,false){
                    @Override
                    public void update(){}
                    @Override
                    public void onClick(MouseEvent event){
                        if(tower.upgrade(StatType.DAMAGE,tower.getDamage() * 1.1) == Tower.MAX_UPGRADE_LEVEL){
                            onMaxUpgradeReached(this.getAssociatedValue(),this);
                        }
                    }
                }.setUpdateDelay(500).setImage2(ContentEngine.BUTTONS.getImage("DMGUP")),
                new TickButton<>(Point2D.ZERO,0,0,RText.EMPTY,tower,StatType.RANGE,false){
                    @Override
                    public void update(){}
                    @Override
                    public void onClick(MouseEvent event){
                        if(tower.upgrade(StatType.RANGE,tower.getRangeBase() * 1.1) == Tower.MAX_UPGRADE_LEVEL){
                            onMaxUpgradeReached(this.getAssociatedValue(),this);
                        }
                    }
                }.setUpdateDelay(500).setImage2(ContentEngine.BUTTONS.getImage("RNGUP")),
                new TickButton<>(Point2D.ZERO,0,0,RText.EMPTY,tower,StatType.ATTACK_SPEED,false){
                    @Override
                    public void update(){}
                    @Override
                    public void onClick(MouseEvent event){
                        if(tower.upgrade(StatType.ATTACK_SPEED,tower.getAtkSpeed() * 1.1) == Tower.MAX_UPGRADE_LEVEL){
                            onMaxUpgradeReached(this.getAssociatedValue(),this);
                        }
                    }
                }.setUpdateDelay(500).setImage2(ContentEngine.BUTTONS.getImage("SPDUP"))
        ));
        augmentDisplay.setBackgroundColor(Color.TRANSPARENT);
        upgradeButtons.setBackgroundColor(Color.TRANSPARENT);
    }

    public void render(GraphicsContext gc){
        gc.setFill(background);
        gc.fillRoundRect(position.getX(), position.getY(), sizeX, sizeY, someArcNumber, someArcNumber);

        text.render(gc);
    }

    @Override
    public synchronized void tick(){
        text.setText(tower.getStats());
    }

    public void addNewAugment(Augment aug){
        augmentDisplay.addIfAbsent(new OpenDisplayableDisplayButton<>(Point2D.ZERO,Point2D.ZERO,tower,aug));
        display.requestGUIReset(true);
    }

    public void onMaxUpgradeReached(StatType t, Button b){
        upgradeButtons.replace(b,getInvocationSelectionButton(t));
    }

    public void onInvocationSelected(Invocation invocation, Button b){
        invocation.applyToTower(tower);
        upgradeButtons.replace(b,new OpenDisplayableDisplayButton<>(b.getPosition(),b.getDimensions(),tower,invocation));
    }

    private Button getInvocationSelectionButton(StatType t) {
        return new InvocationSelectionButton(t, tower,this);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setRenderingPriority(double newPrio){this.renderingPriority = newPrio;}
    public void setPosition(Point2D p){
        text.setPosition(p);
    }
    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }
    @Override
    public void spawn() {
        super.spawn();
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
        augmentDisplay.spawn();
        upgradeButtons.spawn();
        upgradeCostText.spawn();
        isSpawned = true;
    }

    public void destroy() {
        super.destroy();
        Renderable.expended.add(this);
        Tickable.expended.add(this);
        augmentDisplay.destroy();
        upgradeButtons.destroy();
        upgradeCostText.destroy();
        isSpawned = false;
    }

    public Point2D getExtremeties() {
        return new Point2D(
                sizeX,
                position.getY() + sizeY + upgradeButtons.getDimensions().getY() + upgradeCostText.getDimensions().getY()
        );
    }

    public void setStatText(String newText) {
        text.setText(newText);
    }


    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() >= position.getX() && pos.getX() <= position.getX() + sizeX)
                &&
                (pos.getY() >= position.getY() && pos.getY() <= position.getY() + sizeY);
    }

    @Override
    public void deselect() {
        destroy();
    }

    @Override
    public Clickable getRoot(){
        return tower;
    }
}
