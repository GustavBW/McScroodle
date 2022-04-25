package gbw.tdg.towerdefensegame.UI.vfx;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TopDownStrikeVFX extends VFX{

    public TopDownStrikeVFX(int lifetime, double rendPrio, Point2D position) {
        super(lifetime, rendPrio);
        super.position = position;
        super.dim = new Point2D(5,5);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.AQUA);
        gc.fillRect(position.getX() - dim.getX(), 0, dim.getX() * 2, position.getY());
    }
}
