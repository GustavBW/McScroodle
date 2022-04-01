package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IOwnedRenderable;
import gbw.tdg.towerdefensegame.IRenderableOwner;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ProgressBar implements Tickable, IOwnedRenderable {

    private final static double renderingPriority = 45D;
    protected final double min, max, size;
    protected double current;
    private final boolean backdrop;
    private Point2D position;
    protected IRenderableOwner owner;

    public ProgressBar(double min, double max, double size, boolean backdrop, Point2D position, IRenderableOwner owner){
        this.min = min;
        this.max = max;
        this.size = size;
        this.backdrop = backdrop;
        this.current = max;
        this.position = position;
        this.owner = owner;
    }

    public ProgressBar(double size, boolean backdrop, Point2D position, IRenderableOwner owner){
        this(0,1,size,backdrop, position,owner);
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
        return owner.getPosition().add(position);
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    @Override
    public IRenderableOwner getOwner(){
        return owner;
    }
    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
    }

    @Override
    public void tick() {

    }
}
