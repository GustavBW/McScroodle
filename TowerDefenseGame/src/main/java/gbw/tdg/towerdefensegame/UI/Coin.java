package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.backend.Point2G;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Coin implements Renderable, Tickable {

    private double renderingPriority;
    private final Color color = Color.GOLD;
    private final Color colorInner = Color.GOLDENROD;
    private final double value;
    private double radius = 20, lengthMoved;
    private final double rollLength;
    private Point2D position;
    private Point2D velocity;
    private double rollSpeed = 10;
    private long killTimestamp;

    public Coin(double value, Point2D p, double maxRollLength, double renderPrio){
        this(value,p, Point2G.getRandomVector(),maxRollLength,renderPrio);
    }
    public Coin(double value, Point2D p, Point2D velocity, double maxRollLength, double renderPrio){
        this.value = value;
        this.rollLength = maxRollLength * Main.random.nextDouble();
        this.renderingPriority = renderPrio;
        this.position = p;
        this.rollSpeed = rollLength * 0.1;
        this.velocity = velocity;
        killTimestamp = (long) (System.currentTimeMillis() + 3000 + (1000 * Main.random.nextDouble()));
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
        Point2D nextMove = velocity.multiply(rollSpeed);
        lengthMoved += nextMove.magnitude();
        position = position.add(nextMove);

        if(lengthMoved < rollLength) {
            rollSpeed *= 0.9;
        }else{
            rollSpeed = 0;
        }

        if(checkMousePosition()){
            onCollected(1);
        }
        if(System.currentTimeMillis() >= killTimestamp){
            onCollected(0.7);
        }
    }
    private boolean checkMousePosition() {
        double dist = MouseHandler.mousePos.subtract(position).magnitude();
        return dist <= radius * 2;
    }
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRoundRect(position.getX(),position.getY(),radius,radius,radius,radius);
        gc.setFill(colorInner);
        gc.fillRoundRect(position.getX()+3,position.getY()+3,radius-3,radius-3,radius-3,radius-3);
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
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
    }
    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }
    @Override
    public void setDimensions(Point2D dim) {
        this.radius = dim.getX();
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(radius,0);
    }

    private void onCollected(double percentage){
        Main.alterGoldAmount(value * percentage);
        this.destroy();
    }
}
