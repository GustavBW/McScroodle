package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public abstract class Button implements Clickable, Renderable {

    public static final Point2D STANDARD_TEXT_OFFSET = new Point2D(1,1);
    protected double renderingPriority = 85D, rimOffset = 5;
    protected Point2D position;
    protected double sizeX, sizeY;
    protected final RText text;
    protected boolean disabled, shouldRenderBackground,isSpawned = false;
    protected Color rimColor;
    protected Color enabledColor = new Color(1,1,1,1);
    protected Color disabledColor = new Color(0,0,0,0.5);
    protected Color backgroundColor = enabledColor;
    private Clickable root;

    public Button(Button b) {
        this.renderingPriority = b.getRenderingPriority();
        this.rimOffset = b.getRimOffset();
        this.position = b.getPosition();
        this.sizeX = b.getDimensions().getX();
        this.sizeY = b.getDimensions().getY();
        this.text = b.getText();
        this.disabled = b.isDisabled();
        this.shouldRenderBackground = b.rendersBackground();
        this.rimColor = b.getRimColor();
        this.enabledColor = b.getEnabledColor();
        this.disabledColor = b.getDisabledColor();
        this.backgroundColor = b.getBackgroundColor();
        this.root = b.getRoot();
        this.isSpawned = b.isSpawned();
    }

    public Button(Point2D position, double sizeX, double sizeY, RText textUnit, Clickable root, boolean shouldRenderBackground){
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.root = root;
        this.text = textUnit;
        this.shouldRenderBackground = shouldRenderBackground;
        rimColor = new Color(1,1,1,1);
    }
    public Button(Point2D position, double sizeX, double sizeY, RText textUnit, boolean shouldRenderBackground){
        this(position,sizeX,sizeY,textUnit,null,shouldRenderBackground);
    }
    public Button(Point2D position, double sizeX, double sizeY){
        this(position,sizeX,sizeY,RText.EMPTY,null,true);
    }
    public Button(Point2D position, double sizeX, double sizeY, RText textUnit){
        this(position,sizeX,sizeY,textUnit,null,false);
    }

    @Override
    public void render(GraphicsContext gc){
        if(shouldRenderBackground) {
            gc.setFill(backgroundColor);
            gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);

            gc.setFill(rimColor);
            gc.fillRect(position.getX() - rimOffset, position.getY() - rimOffset, sizeX + (2 * rimOffset), sizeY + (2 * rimOffset));

            gc.setFill(Color.BLACK);
            gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);
        }

        text.render(gc);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setBackgroundColor(Color color){
        enabledColor = color;
        backgroundColor = color;
    }
    public void setDisabledColor(Color color){disabledColor = color;}
    public void setRimColor(Color color){
        rimColor = color;
    }
    public void setShouldRenderBackground(boolean state){
        shouldRenderBackground = state;
    }
    public boolean rendersBackground(){
        return shouldRenderBackground;
    }
    public RText getText(){
        return text;
    }
    public double getRimOffset(){
        return rimOffset;
    }
    public boolean isDisabled(){return disabled;}
    private Color getRimColor() {
        return rimColor;
    }
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    public Color getDisabledColor() {
        return disabledColor;
    }
    public Color getEnabledColor() {
        return enabledColor;
    }
    public boolean isSpawned(){return isSpawned;}

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setRenderingPriority(double newPrio){this.renderingPriority = newPrio;}
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
    public void onClick(MouseEvent event) {}
    @Override
    public void onPress(MouseEvent event) {}
    @Override
    public void onRelease(MouseEvent event) {}

    @Override
    public void spawn() {
        Clickable.newborn.add(this);
        Renderable.newborn.add(this);
        isSpawned = true;
    }

    @Override
    public void destroy() {
        Clickable.expended.add(this);
        Renderable.expended.add(this);
        isSpawned = false;
    }

    @Override
    public void deselect(){}

    @Override
    public Clickable getRoot(){return root == null ? this : root;}
    public void setRoot(Clickable c){
        this.root = c;
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
