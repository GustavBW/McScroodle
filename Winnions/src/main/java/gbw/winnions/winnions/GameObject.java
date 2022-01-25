package gbw.winnions.winnions;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {

    public static List<GameObject> gameObjects = new ArrayList<>();

    protected GameObject(){gameObjects.add(this);}

    public abstract Point2D getPosition();
    public abstract void destroy();

}
