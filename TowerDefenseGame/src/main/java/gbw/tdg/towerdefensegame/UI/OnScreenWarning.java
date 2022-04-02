package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.format.TextStyle;

public class OnScreenWarning implements Renderable, Tickable {

    private final double renderingPriority = 95D;
    private final RText text;
    private final Point2D pos;
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
}
