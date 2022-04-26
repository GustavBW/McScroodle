package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.UI.buttons.Button;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ClickableIcon<T> extends Button{

    private Image image;
    private boolean fixedScaling;
    private T objRepresented;

    public ClickableIcon(Image image, double rendPrio, Point2D dim, Point2D position, boolean fixedScaling, T t) {
        super(position, dim.getX(), dim.getY(),RText.EMPTY, false);
        this.image = image;
        this.fixedScaling = fixedScaling;
        this.objRepresented = t;
        super.renderingPriority = rendPrio;
    }

    public ClickableIcon(ClickableIcon<T> cI){
        super(cI);
        this.image = cI.getImage();
        this.fixedScaling = cI.isFixedScaling();
        this.objRepresented = cI.getObjRepresented();
        super.renderingPriority = cI.getRenderingPriority();
    }

    public boolean isFixedScaling(){
        return fixedScaling;
    }
    public Image getImage() {
        return image;
    }

    @Override
    public void spawn() {
        super.spawn();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image,position.getX(),position.getY(),sizeX,sizeY);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() >= position.getX() && pos.getX() <= position.getX() + sizeX)
                &&
                (pos.getY() >= position.getY() && pos.getY() <= position.getY() + sizeY);
    }

    @Override
    public void onClick(MouseEvent event) {
        System.out.println("ClickableIcon clicked");
    }

    @Override
    public void onPress(MouseEvent event) {

    }

    @Override
    public void onRelease(MouseEvent event) {

    }

    @Override
    public void deselect() {
        super.deselect();
        destroy();
    }
    public void setFixedScaling(boolean state){
        this.fixedScaling = state;
    }
    public T getObjRepresented(){return objRepresented;}

    @Override
    public void setDimensions(Point2D dim) {
        if(fixedScaling){
            if(dim.getX() < dim.getY()) {
                dim = new Point2D(dim.getX(), dim.getX());
            }else if(dim.getY() < dim.getX()){
                dim = new Point2D(dim.getY(), dim.getY());
            }
        }
        this.sizeX = dim.getX();
        this.sizeY = dim.getY();
    }

    public ClickableIcon setRoot2(Clickable root){
        super.setRoot(root);
        return this;
    }
}
