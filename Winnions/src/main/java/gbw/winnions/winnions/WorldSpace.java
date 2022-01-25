package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class WorldSpace implements Renderable{

    public static final Point2D worldDimensions = new Point2D(4000,4000);
    private static List<RenderableLayerTypeCombo> addNextPass = new ArrayList<>();
    private static List<RenderableLayerTypeCombo> removeNextPass = new ArrayList<>();

    private List<RenderLayer> layers;

    public WorldSpace(){

        List<RenderableSquare> squares = new ArrayList<>();
        layers = new ArrayList<>();

        for(LayerType t : LayerType.values()){
            RenderLayer r = new RenderLayer(t);
            layers.add(r);
        }

        for(int i = 0; i * 100 < worldDimensions.getX(); i++){
            for(int j = 0; j * 100 < worldDimensions.getY(); j++) {
                RenderableSquare square = new RenderableSquare(new Point2D(j * 100, i * 100), 10);
                squares.add(square);
            }
        }

        for(RenderLayer r : layers){
            if(r.getType() == LayerType.Background0){
                r.getChildren().addAll(squares);
                break;
            }
        }

    }

    @Override
    public void render(GraphicsContext gc, Point2D worldSpaceOffset) {

        worldSpaceOffset = new Point2D(Game.localPlayerCamera.getPosition().getX(), Game.localPlayerCamera.getPosition().getY());

        for(RenderLayer l : layers){
            l.render(gc, worldSpaceOffset);
        }

        updateLayers();
    }

    private void updateLayers(){

        for(RenderLayer l : layers){
            for(RenderableLayerTypeCombo rC : addNextPass){
                if(l.getType() == rC.getType()){
                    l.getChildren().add(rC.getObj());
                }
            }
        }

        for(RenderLayer l : layers){
            for(RenderableLayerTypeCombo rC : removeNextPass){
                if(l.getType() == rC.getType()){
                    l.getChildren().remove(rC.getObj());
                }
            }
        }

        addNextPass.clear();
        removeNextPass.clear();
    }



    public static void addRenderable(Renderable r, LayerType t){
        addNextPass.add(new RenderableLayerTypeCombo(r,t));
    }
    public static void removeRenderable(Renderable r, LayerType t){
        removeNextPass.add(new RenderableLayerTypeCombo(r,t));
    }

}
