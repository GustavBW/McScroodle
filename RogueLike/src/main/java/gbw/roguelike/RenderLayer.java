package gbw.roguelike;

import gbw.roguelike.interfaces.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class RenderLayer implements Renderable {

    private final ArrayList<Renderable> renderables;

    public RenderLayer(){
        renderables = new ArrayList<>();
    }

    public void render(GraphicsContext gc){
        for(Renderable r : renderables){
            r.render(gc);
        }
    }




}
