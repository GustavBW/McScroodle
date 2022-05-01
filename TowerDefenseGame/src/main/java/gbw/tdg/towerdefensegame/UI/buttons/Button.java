package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.backend.Point2G;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;

public abstract class Button implements Clickable, Renderable {

    //The positions and dimensions of the image can be set through the respective offset and dimension "ratios"
    //these ratios are in comparison to the buttons position and size. E.g. a ratio 2 / 3rds for the image
    //offset ratio, would mean that the image is drawn from the position of the button + 2 / 3rds of the buttons dimension.

    public static final Point2D STANDARD_TEXT_OFFSET = Point2G.DOWN_RIGHT;
    protected double renderingPriority = 85D, rimOffset = 5;
    protected Point2D position, imageOffsetRatios = Point2D.ZERO, imageOffset = Point2G.ONE, imageDimRatios = Point2G.ONE;
    protected double sizeX, sizeY;
    protected final RText text;
    protected boolean disabled, shouldRenderBackground,isSpawned = false;
    protected Color rimColor;
    protected Color enabledColor = new Color(1,1,1,1);
    protected Color disabledColor = new Color(0,0,0,0.5);
    protected Color backgroundColor = enabledColor;
    private Clickable root;
    private Image image;

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
        this.image = b.getImage();
        this.imageOffsetRatios = b.getImageOffsetRatios();
        this.imageDimRatios = b.getImageDimRatios();
        this.imageOffset = b.getImageOffset();
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
    public Button(Image image, Point2D position, Point2D dim, RText textUnit, Clickable root){
        this(position,dim.getX(),dim.getY(),textUnit,root,false);
        this.image = image;
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
        if(image != null){
            gc.drawImage(
                    image, position.getX() + imageOffset.getX(),position.getY() + imageOffset.getY(),
                    sizeX * imageDimRatios.getX(),sizeY * imageDimRatios.getY()
            );
        }

        text.render(gc);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    private Image getImage() {
        return image;
    }
    public void setImage(Image image){this.image = image;}
    public void setBackgroundColor(Color color){
        enabledColor = color;
        backgroundColor = color;
    }
    public void setImageOffsetRatios(Point2D ratios){
        this.imageOffsetRatios = ratios;
        this.setPosition(position);
    }
    public void setImageOffset(Point2D offset){
        this.imageOffset = offset;
    }
    public void setImageDimRatios(Point2D ratios){
        imageDimRatios = ratios;
    }
    private Point2D getImageDimRatios() {
        return this.imageDimRatios;
    }
    public Point2D getImageOffsetRatios(){
        return imageOffsetRatios;
    }
    public Point2D getImageOffset(){
        return this.imageOffset;
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
        this.imageOffset = new Point2D(
                sizeX * imageOffsetRatios.getX(),
                sizeY * imageOffsetRatios.getY()
        );
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
