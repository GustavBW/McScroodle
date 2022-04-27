package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.backend.Decimals;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.augments.Augment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AugmentBuyButton extends BounceBackButton{

    private final Augment augment;
    private final IClickableOwner owner;
    private double price,ogPrice, margin = 10 * Main.scale.getY();
    private final SARText descText;
    private boolean showDesc = true, showIcon = false;
    private Point2D iconOffset, descOffset = new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.045);
    private Point2D titleOffset = new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.025);

    public AugmentBuyButton(Point2D position, double sizeX, double sizeY, RText textUnit, Augment augment, IClickableOwner owner, double price) {
        super(position, sizeX, sizeY, textUnit,null);
        super.setBackgroundColor(new Color(0,0,0,0.5));
        this.augment = augment;
        augment.getIcon().setDimensions(new Point2D(sizeY - (2 * rimOffset),sizeY - (2 * rimOffset)));
        this.iconOffset = new Point2D((sizeX) - (rimOffset + augment.getIcon().getDimensions().getX()), rimOffset);
        this.owner = owner;
        this.ogPrice = price;
        this.price = Decimals.toXDecimalPlaces(price + augment.getWorth(),2);
        super.text.setText(augment.getName());
        List<String> formattedDesc = TextFormatter.toLinesArray(augment.getDesc(), (int) (30 * (Main.scale.getX()))," ");
        formattedDesc.add(this.price + "S");
        this.descText = SARText.create(formattedDesc, position,new Point2D(sizeX - descOffset.getX(),sizeY - textUnit.getSize()),2, renderingPriority)
                .setFont(Font.font("Verdana", Main.canvasSize.getX() * 0.001))
                .setFittingFontSize(false)
                .setPrefferedFontSize(30 * Main.scale.getX());
    }

    public AugmentBuyButton(RText textUnit, Augment augment, IClickableOwner owner, int price){
        this(new Point2D(0,0),0,0,textUnit,augment,owner,price);
    }
    public AugmentBuyButton(AugmentBuyButton aBB){
        this(aBB.getPosition(),aBB.getDimensions().getX(),aBB.getDimensions().getY(),aBB.getText(),aBB.getAugment(),aBB.getOwner(),aBB.getOgPrice());
    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);
        if(showDesc) {
            descText.render(gc);
        }
    }

    @Override
    public void spawn(){
        super.spawn();
        if(showIcon){
            augment.getIcon().spawn();
        }
    }
    @Override
    public void destroy(){
        super.destroy();
        if(showIcon) {
            augment.getIcon().destroy();
        }
    }
    public Augment getAugment(){return augment;}
    public IClickableOwner getOwner(){
        return owner;
    }
    public double getOgPrice(){
        return ogPrice;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setShowDesc(boolean state){
        showDesc = state;
    }
    public void setShowIcon(boolean state){
        showIcon = state;
    }
    public ClickableIcon getIcon(){
        return augment.getIcon();
    }

    @Override
    public void setRenderingPriority(double newPrio){
        if(showIcon){
            getIcon().setRenderingPriority(newPrio+.1);
        }
        super.setRenderingPriority(newPrio);
        descText.setRenderingPriority(newPrio+.1);
    }

    @Override
    public void setPosition(Point2D p) {
        position = p;
        super.text.setPosition(p.add(titleOffset));
        descText.setPosition(p.add(descOffset));
        if(showIcon){
            getIcon().setPosition(p.add(iconOffset));
        }
    }

    @Override
    public void setDimensions(Point2D dim){
        super.setDimensions(dim);
        descText.setDimensions(new Point2D(dim.getX(), dim.getY() - (text.getSize() + margin)));
        if(showIcon){
            getIcon().setDimensions(dim.subtract(rimOffset,rimOffset));
        }
    }

    @Override
    public void onClick(MouseEvent event){
        if(Main.getSouls() >= price) {
            Main.alterSoulsAmount(-price);
            owner.onChildPress(this,event);
        }else{
            new OnScreenWarning("Not enough Souls!", Main.canvasSize.multiply(0.5), 3).spawn();
        }
    }

}
