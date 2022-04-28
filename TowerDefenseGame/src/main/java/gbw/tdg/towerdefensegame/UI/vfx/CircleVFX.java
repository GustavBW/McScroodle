package gbw.tdg.towerdefensegame.UI.vfx;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleVFX extends VFX {

    private double radius;
    private Color color = VFX.DEFAULT_COLOR;

    public CircleVFX(int lifetime, double rendPrio, Point2D position, double radius) {
        super(lifetime, rendPrio);
        this.radius = radius;
        this.position = position;
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(color);
        gc.fillRoundRect(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2, radius * 2, radius* 2);
    }

    public void setRadius(double r){
        this.radius = r;
    }

    public CircleVFX setColor(Color color){
        this.color = color;
        return this;
    }

}
