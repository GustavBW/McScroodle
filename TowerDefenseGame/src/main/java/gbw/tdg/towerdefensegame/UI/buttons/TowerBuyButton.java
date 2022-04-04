package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TowerBuyButton extends Button implements Tickable {

    private final Tower tower;
    private final IClickableOwner owner;
    private int clickCount;
    private int price;
    private boolean disabled;
    private final Color enabledColor = new Color(1,1,1,1);
    private final Color disabledColor = new Color(0,0,0,0.5);
    private Color backgroundColor = enabledColor;

    public TowerBuyButton(Point2D position, double sizeX, double sizeY, RText textUnit, Tower tower, IClickableOwner owner, int price) {
        super(position, sizeX, sizeY, textUnit);
        this.tower = tower;
        this.owner = owner;
        this.price = price;
        textUnit.setText(tower.toString() + "\nPrice: " + price);
    }
    public TowerBuyButton(RText textUnit, Tower tower, IClickableOwner owner, int price){
        this(new Point2D(0,0),0,0,textUnit,tower,owner,price);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(),position.getY(),sizeX,sizeY);
        text.render(gc);
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
        super.text.setPosition(p.add(30,20));
    }

    @Override
    public void tick(){
        if(clickCount == 1){
            position = MouseHandler.mousePos;
        }
    }

    public Tower getTower(){
        return tower;
    }

    @Override
    public void onInteraction(){
        if(Main.getGold() >= price) {
            Main.alterGoldAmount(-price);
            owner.childClicked(this);
        }else{
            new OnScreenWarning("Not enough gold!", Main.canvasSize.multiply(0.5), 3).spawn();
        }
    }

    public void setDisabled(boolean disabled){
        if(disabled){
            Clickable.expended.add(this);
            backgroundColor = disabledColor;
        }else{
            Clickable.newborn.add(this);
            backgroundColor = enabledColor;
        }
    }
}
