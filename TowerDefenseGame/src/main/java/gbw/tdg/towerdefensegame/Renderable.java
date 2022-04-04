package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface Renderable extends IGameObject{

    List<Renderable> active = new LinkedList<>();
    Set<Renderable> newborn = new HashSet<>();
    Set<Renderable> expended = new HashSet<>();

    void render(GraphicsContext gc);
    Point2D getPosition();
    double getRenderingPriority();
    void setPosition(Point2D p);
    void setDimensions(Point2D dim);
}
