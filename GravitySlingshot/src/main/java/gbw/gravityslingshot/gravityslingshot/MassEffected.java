package gbw.gravityslingshot.gravityslingshot;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface MassEffected extends IGameObject{

    List<MassEffected> active = new ArrayList<>();
    List<MassEffected> expended = new ArrayList<>();
    List<MassEffected> newborn = new ArrayList<>();

    void evaluate(MassEffected m);
    double getMass();

}
