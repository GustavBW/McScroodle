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
    private Point2D pos;
    private final int duration;
    private long spawnTimeStamp;

    public OnScreenWarning(String txt, Point2D pos, int duration){
        text = new RText(txt, pos, 2, Color.RED, Font.font("Impact", 50));
        this.pos = pos;
        this.duration = duration;
    }

    @Override
    public void spawn() {
        spawnTimeStamp = System.nanoTime();
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
    }

    @Override
    public void tick(){
        if(System.nanoTime() > spawnTimeStamp + (duration * 1_000_000_000L)){
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

    }
}
