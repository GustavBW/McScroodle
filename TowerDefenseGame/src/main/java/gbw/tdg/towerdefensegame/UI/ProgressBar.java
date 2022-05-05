package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ProgressBar implements Renderable {

    private double renderingPriority = 45D;
    protected final double min, max;
    protected double sizeX,sizeY;
    protected double current;
    private final boolean backdrop;
    protected Point2D position;

    public ProgressBar(double min, double max, double size, boolean backdrop, Point2D position){
        this.min = min;
        this.max = max;
        this.sizeX = size;
        this.sizeY = sizeX * 0.1;
        this.backdrop = backdrop;
        this.current = max;
        this.position = position;
    }

    public ProgressBar(double size, boolean backdrop, Point2D position){
        this(0,1,size,backdrop, position);
    }

    @Override
    public void render(GraphicsContext gc){
        if(backdrop) {
            gc.setFill(Color.BLACK);    //Backdrop
            gc.fillRect(position.getX() - sizeX / 2, position.getY(), sizeX, sizeY);
        }

        gc.setFill(Color.RED);
        gc.fillRect(position.getX() - sizeX / 2, position.getY(), sizeX * (current / max), sizeY);

    }


    public void setVal(double i){this.current = i;}

    public double getVal(){return current;}
    public void setPosition(Point2D p){this.position = p;}

    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(sizeX, sizeY);
    }

    public Point2D getPosition(){
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
    }

}
