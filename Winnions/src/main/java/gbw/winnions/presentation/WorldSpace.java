package gbw.winnions.presentation;

import gbw.winnions.Game;
import gbw.winnions.domain.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class WorldSpace implements Renderable, Tickable {

    public static final Point2D worldDimensions = new Point2D(4000,4000);
    public static Point2D currentWorldSpaceOffset = new Point2D(0,0);
    private static List<RenderableLayerTypeCombo> addNextPass = new ArrayList<>();
    private static List<RenderableLayerTypeCombo> removeNextPass = new ArrayList<>();

    private List<RenderLayer> layers;

    public WorldSpace(){

        List<RenderableSquare> squares = new ArrayList<>();
        List<Collidable> colSquares = new ArrayList<>();
        layers = new ArrayList<>();

        for(LayerType t : LayerType.values()){
            RenderLayer r = new RenderLayer(t);
            layers.add(r);
        }

        for(int i = 0; i * 200 < worldDimensions.getX(); i++){
            for(int j = 0; j * 200 < worldDimensions.getY(); j++) {
                RenderableSquare square = new RenderableSquare(new Point2D(j * 200, i * 200), 20);
                squares.add(square);
                colSquares.add(square);
            }
        }

        CollisionHandler.addAllCollidable(colSquares);

        for(RenderLayer r : layers){
            if(r.getType() == LayerType.Background0){
                r.getChildren().addAll(squares);
                break;
            }
        }

    }

    @Override
    public void tick(){
        currentWorldSpaceOffset = new Point2D( - Game.localPlayerCamera.getPosition().getX(),  - Game.localPlayerCamera.getPosition().getY());
    }

    @Override
    public void render(GraphicsContext gc) {

        for(RenderLayer l : layers){
            l.render(gc);
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
    public static void addAllRenderable(List<Renderable> list, LayerType t){
        for(Renderable r : list){
            addRenderable(r,t);
        }
    }
    public static void removeRenderable(Renderable r, LayerType t){
        removeNextPass.add(new RenderableLayerTypeCombo(r,t));
    }
    public static void removeAllRenderable(List<Renderable> list, LayerType t){
        for(Renderable r : list) {
            removeRenderable(r,t);
        }
    }

}
