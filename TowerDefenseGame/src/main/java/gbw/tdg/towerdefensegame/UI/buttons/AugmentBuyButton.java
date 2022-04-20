package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Decimals;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.IClickableOwner;
import gbw.tdg.towerdefensegame.UI.OnScreenWarning;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.augments.Augment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AugmentBuyButton extends Button{

    private final Augment augment;
    private final IClickableOwner owner;
    private final double price,ogPrice;
    private final RText descText;
    private boolean showDesc = true;

    public AugmentBuyButton(Point2D position, double sizeX, double sizeY, RText textUnit, Augment augment, IClickableOwner owner, double price) {
        super(position, sizeX, sizeY, textUnit,true);
        super.setBackgroundColor(new Color(0,0,0,0.5));
        this.augment = augment;
        this.owner = owner;
        this.ogPrice = price;
        this.price = Decimals.toXDecimalPlaces(price + augment.getWorth(),2);
        super.text.setText(augment.getName());
        String formattedDesc = TextFormatter.toLines(augment.getDesc(), (int) (15 * (Main.canvasSize.getX() * (1 / 1920.0)))," ") + this.price + "S";
        this.descText = new RText(formattedDesc, position,2, Color.WHITE, Font.font("Verdana", Main.canvasSize.getX()*0.007));
    }
    public AugmentBuyButton(RText textUnit, Augment augment, IClickableOwner owner, int price){
        this(new Point2D(0,0),0,0,textUnit,augment,owner,price);
    }
    public AugmentBuyButton(AugmentBuyButton aBB, boolean showDesc){
        this(aBB.getPosition(),aBB.getDimensions().getX(),aBB.getDimensions().getY(),aBB.getText(),aBB.getAugment(),aBB.getOwner(),aBB.getOgPrice());
        this.showDesc = showDesc;
    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);
        if(showDesc) {
            descText.render(gc);
        }
    }

    public Augment getAugment(){return augment;}
    public IClickableOwner getOwner(){
        return owner;
    }
    public double getOgPrice(){
        return ogPrice;
    }


    @Override
    public void setPosition(Point2D p) {
        position = p;
        super.text.setPosition(p.add(new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.025)));
        descText.setPosition(p.add(new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.045)));
    }

    @Override
    public void onInteraction(){
        if(Main.getSouls() >= price) {
            Main.alterSoulsAmount(-price);
            owner.childClicked(this);
        }else{
            new OnScreenWarning("Not enough Souls!", Main.canvasSize.multiply(0.5), 3).spawn();
        }
    }

}
