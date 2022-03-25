package gbw.gravityslingshot.gravityslingshot;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public interface Renderable {

    List<Renderable> active = new ArrayList<>();
    List<Renderable> expended = new ArrayList<>();
    List<Renderable> newborn = new ArrayList<>();

    public void render(GraphicsContext gc);

}
