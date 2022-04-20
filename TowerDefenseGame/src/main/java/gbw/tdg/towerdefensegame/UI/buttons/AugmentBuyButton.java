package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.IClickableOwner;
import gbw.tdg.towerdefensegame.UI.OnScreenWarning;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.TextFormatter;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AugmentBuyButton extends Button{

    private final Augment augment;
    private final IClickableOwner owner;
    private final int price;
    private final RText descText;

    public AugmentBuyButton(Point2D position, double sizeX, double sizeY, RText textUnit, Augment augment, IClickableOwner owner, int price) {
        super(position, sizeX, sizeY, textUnit,true);
        super.setBackgroundColor(new Color(0,0,0,0.5));
        this.augment = augment;
        this.owner = owner;
        this.price = (int) (price + augment.getWorth());
        super.text.setText(augment.getName());
        String formattedDesc = TextFormatter.toLines(augment.getDesc(),30," ") + " " + price + "G";
        this.descText = new RText(formattedDesc, position,2, Color.WHITE, Font.font("Verdana", Main.canvasSize.getX()*0.007));
    }
    public AugmentBuyButton(RText textUnit, Augment augment, IClickableOwner owner, int price){
        this(new Point2D(0,0),0,0,textUnit,augment,owner,price);
    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);
        descText.render(gc);
    }

    public Augment getAugment(){return augment;}

    @Override
    public void setPosition(Point2D p) {
        position = p;
        super.text.setPosition(p.add(new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.025)));
        descText.setPosition(p.add(new Point2D(Main.canvasSize.getX() * 0.00762, Main.canvasSize.getY() * 0.045)));
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
