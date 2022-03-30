package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.Set;

public interface Renderable extends IGameObject{

    Set<Renderable> active = new HashSet<>();
    Set<Renderable> newborn = new HashSet<>();
    Set<Renderable> expended = new HashSet<>();

    void render(GraphicsContext gc);
    Point2D getPosition();
    double getRenderingPriority();
}
