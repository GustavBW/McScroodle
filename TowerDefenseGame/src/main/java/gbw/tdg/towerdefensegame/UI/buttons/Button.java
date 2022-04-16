package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button implements Clickable, Renderable {

    public static final Point2D STANDARD_TEXT_OFFSET = new Point2D(1,1);
    protected double renderingPriority = 85D;
    protected Point2D position;
    protected double sizeX, sizeY;
    protected final RText text;
    protected boolean disabled;
    protected Color rimColor;
    protected Color enabledColor = new Color(1,1,1,1);
    protected Color disabledColor = new Color(0,0,0,0.5);
    protected Color backgroundColor = enabledColor;

    public Button(Point2D position, double sizeX, double sizeY, RText textUnit){
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = textUnit;
        rimColor = new Color(1,1,1,1);
    }

    public Button(Point2D position, double sizeX, double sizeY){
        this(position,sizeX,sizeY,new RText("",new Point2D(0,0), 0, Color.BLACK, Font.font("Impact")));
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);

        gc.setFill(rimColor);
        gc.fillRect(position.getX()-5, position.getY()-5, sizeX +10, sizeY +10);

        gc.setFill(Color.BLACK);
        gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);

        text.render(gc);
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
    public void setPosition(Point2D p) {
        this.position = p;
        text.setPosition(p.add(STANDARD_TEXT_OFFSET.multiply(text.getSize())));
    }

    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }
    public Point2D getDimensions(){
        return new Point2D(sizeX,sizeY);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        if(disabled){return false;}
        //System.out.println("Checking isInBounds() with pos " + pos.getX() + " " +pos.getY());
        return (pos.getX() < position.getX() + sizeX && pos.getX() > position.getX())
                && (pos.getY() < position.getY() + sizeY && pos.getY() > position.getY());
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void spawn() {
        Clickable.newborn.add(this);
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Clickable.expended.add(this);
        Renderable.expended.add(this);
    }

    @Override
    public void deselect(){

    }

    public void setDisable(boolean disabled){
        if(disabled){
            Clickable.expended.add(this);
            backgroundColor = disabledColor;
            this.disabled = true;
        }else{
            Clickable.newborn.add(this);
            backgroundColor = enabledColor;
            this.disabled = false;
        }
    }

}
