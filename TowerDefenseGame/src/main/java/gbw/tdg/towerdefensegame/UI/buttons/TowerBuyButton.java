package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TowerBuyButton extends Button {

    private final Tower tower;
    private final IClickableOwner owner;
    private int price;

    public TowerBuyButton(Point2D position, double sizeX, double sizeY, RText textUnit, Tower tower, IClickableOwner owner, int price) {
        super(position, sizeX, sizeY, textUnit);
        renderingPriority = 99.9;
        this.tower = tower;
        this.owner = owner;
        this.price = price;
        super.setBackgroundColor(new Color(0,0,0,0.5));
        super.setDisabledColor(Color.BLACK);
        textUnit.setText(tower.getStats() + "\nPrice: " + price);
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
        position = p;
        super.text.setPosition(p.add(new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.025)));
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
}
