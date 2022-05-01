package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OnScreenWarning implements Renderable, Tickable {

    private double renderingPriority = 88D;
    private final RText text;
    private Point2D pos, vel;
    private final long duration;
    private long spawnTimeStamp;

    public OnScreenWarning(String txt, Point2D pos, int duration){
        text = new RText(txt, pos, 2, Color.RED, Font.font("Impact", 50));
        this.pos = pos;
        this.duration = duration;
        this.vel = Point2D.ZERO;
    }
    public OnScreenWarning(String txt, Point2D pos, int dur, Color color, Font font){
        this(txt,pos,dur);
        this.text.setTextColor(color);
        this.text.setFont(font);
    }

    @Override
    public void spawn() {
        spawnTimeStamp = System.currentTimeMillis();
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
    }

    public OnScreenWarning setFont(Font font){
        this.text.setFont(font);
        return this;
    }
    public OnScreenWarning setColor(Color color){
        this.text.setTextColor(color);
        return this;
    }
    public OnScreenWarning setVel(Point2D vel){
        this.vel = vel;
        return this;
    }

    @Override
    public void tick(){
        pos = pos.add(vel);
        text.setPosition(pos);
        if(System.currentTimeMillis() > spawnTimeStamp + duration * 1_000){
            destroy();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        text.render(gc);
    }

    @Override
    public Point2D getPosition() {
        return pos;
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
        this.pos = p;
        text.setPosition(p);
    }

    @Override
    public void setDimensions(Point2D dim) {
        text.setFont(Font.font(text.getFont().getFamily(),dim.getX()));
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(text.getFont().getSize(), 0);
    }
}
