package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface StaticMass extends IGameObject{
    List<StaticMass> active = new ArrayList<>();
    List<StaticMass> expended = new ArrayList<>();
    List<StaticMass> newborn = new ArrayList<>();

    double getMass();
}
