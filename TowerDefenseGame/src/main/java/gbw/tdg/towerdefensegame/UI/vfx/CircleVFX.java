package gbw.tdg.towerdefensegame.UI.vfx;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class CircleVFX extends VFX {

    private double radius;

    public CircleVFX(int lifetime, double rendPrio, Point2D position, double radius) {
        super(lifetime, rendPrio);
        this.radius = radius;
        this.position = position;
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(VFX.DEFAULT_COLOR);
        gc.fillRoundRect(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2, radius * 2, radius* 2);
    }

}
