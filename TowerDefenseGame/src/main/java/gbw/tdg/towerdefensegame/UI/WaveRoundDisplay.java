package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.handlers.WaveManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WaveRoundDisplay implements Renderable, Tickable {

    private double renderingPriority = 79;
    private int wave, round;
    private Point2D position;
    private Point2D dimensions;
    private WaveManager manager;
    private final RText text;
    private final Color textColor = new Color(1,1,1,1);
    private final Font textFont = Font.font("Impact", Main.canvasSize.getX() * 0.01);
    private final Color backgroundColor = new Color(0,0,0,0.5);
    private final Point2D textOffset = new Point2D(Main.canvasSize.getX() * 0.02865,Main.canvasSize.getY() * 0.0355);


    public WaveRoundDisplay(WaveManager manager, Point2D position, Point2D dimensions){
        this.dimensions = dimensions;
        this.position = position;
        this.manager = manager;
        this.text = new RText("WAVE x ROUND y", position.add(textOffset), 2, textColor, textFont);
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
    public void render(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(),position.getY(),dimensions.getX(),dimensions.getY());

        text.render(gc);
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
        text.setPosition(p.add(textOffset));
    }

    @Override
    public void setDimensions(Point2D dim) {
        this.dimensions = dim;
    }

    @Override
    public void tick() {
        wave = manager.getWaveNumber();
        round = manager.getRoundNumber();
        text.setText("WAVE " + wave + " ROUND " + round);
    }
}
