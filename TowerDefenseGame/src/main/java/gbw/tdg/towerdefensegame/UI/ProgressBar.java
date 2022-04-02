package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IOwnedRenderable;
import gbw.tdg.towerdefensegame.IRenderableOwner;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ProgressBar implements Renderable {

    private final static double renderingPriority = 45D;
    protected final double min, max, size;
    protected double current;
    private final boolean backdrop;
    protected Point2D position;

    public ProgressBar(double min, double max, double size, boolean backdrop, Point2D position){
        this.min = min;
        this.max = max;
        this.size = size;
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
            gc.fillRect(position.getX() - size / 2, position.getY(), size, size * 0.1);
        }

        gc.setFill(Color.RED);
        gc.fillRect(position.getX() - size / 2, position.getY(), size * (current / max), size * 0.1);

    }


    public void setVal(double i){this.current = i;}

    public double getVal(){return current;}
    public void setPosition(Point2D p){this.position = p;}
    public Point2D getPosition(){
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
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
