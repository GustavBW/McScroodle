package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface MassEffected extends IGameObject{

    List<MassEffected> active = new ArrayList<>();
    List<MassEffected> expended = new ArrayList<>();
    List<MassEffected> newborn = new ArrayList<>();

    void evaluateGravity(double force, Point2D direction);
    double getMass();

}
