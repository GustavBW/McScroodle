package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class AssociatedButton<T> extends Button{

    private T obj;

    public AssociatedButton(Button b, T t){
        super(b);
        this.obj = t;
    }

    public AssociatedButton(AssociatedButton<T> b) {
        super(b);
        this.obj = b.getAssociatedObj();
    }

    public AssociatedButton(Point2D position, Point2D dim, RText textUnit, Clickable root, boolean shouldRenderBackground, T t) {
        super(position, dim.getX(), dim.getY(), textUnit, root, shouldRenderBackground);
        this.obj = t;
    }

    public AssociatedButton(Image image, Point2D position, Point2D dim, RText textUnit, Clickable root, T t) {
        super(image, position, dim, textUnit, root);
        this.obj = t;
    }

    public AssociatedButton(Point2D position, double sizeX, double sizeY, RText textUnit, boolean shouldRenderBackground, T t) {
        super(position, sizeX, sizeY, textUnit, shouldRenderBackground);
        this.obj = t;
    }

    public AssociatedButton(Point2D position, double sizeX, double sizeY, T t) {
        super(position, sizeX, sizeY);
        this.obj = t;
    }

    public AssociatedButton(Point2D position, double sizeX, double sizeY, RText textUnit, T t) {
        super(position, sizeX, sizeY, textUnit);
        this.obj = t;
    }

    public T getAssociatedObj(){
        return obj;
    }
}
