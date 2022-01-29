package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class RenderLayer implements Renderable{

    private final LayerType layerType;
    private List<Renderable> renderables;

    public RenderLayer(LayerType t){
        layerType = t;
        renderables = new ArrayList<>();
    }

    @Override
    public void render(GraphicsContext gc) {
        for(Renderable r : renderables){
            r.render(gc);
        }
    }

    public List<Renderable> getChildren(){return renderables;}
    public LayerType getType(){return layerType;}
}
