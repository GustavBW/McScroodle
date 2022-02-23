package gbw.roguelike.ui;

import gbw.roguelike.interfaces.Clickable;
import gbw.roguelike.interfaces.iGameObject;
import javafx.geometry.Point2D;

public class Button implements Clickable, iGameObject {

    protected Point2D size;
    protected Point2D position;

    @Override
    public boolean isInBounds(Point2D p) {
        return (p.getX() < position.getX() + size.getX() && p.getX() > position.getX()) && (p.getY() < position.getY() + size.getY() && p.getY() > position.getY());
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void instantiate() {

    }

    @Override
    public void destroy() {

    }
}
