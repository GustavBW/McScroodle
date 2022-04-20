package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RenderableImage implements Renderable {

    private Image image;
    private double rendPrio;
    protected Point2D dim, position;
    private boolean fixedScaling;

    public RenderableImage(Image image, double rendPrio, Point2D dim, Point2D position, boolean fixedScaling){
        this.image = image;
        this.rendPrio = rendPrio;
        this.dim = dim;
        this.position = position;
        this.fixedScaling = fixedScaling;
    }
    public RenderableImage(Image image, Point2D position){
        this(image,0,new Point2D(image.getWidth(),image.getHeight()),position,true);
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image,position.getX(),position.getY(),dim.getX(),dim.getY());
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return rendPrio;
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }

    @Override
    public void setRenderingPriority(double newPrio) {
        this.rendPrio = newPrio;
    }

    @Override
    public void setDimensions(Point2D dim) {
        if(fixedScaling){
            if(dim.getX() < dim.getY()) {
                dim = new Point2D(dim.getX(), dim.getX());
            }else if(dim.getY() < dim.getX()){
                dim = new Point2D(dim.getY(), dim.getY());
            }
        }
        this.dim = dim;
    }

    public Point2D getDim(){
        return dim;
    }
}
