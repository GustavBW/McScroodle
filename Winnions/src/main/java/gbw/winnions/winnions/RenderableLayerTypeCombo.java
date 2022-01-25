package gbw.winnions.winnions;

public class RenderableLayerTypeCombo {

    private LayerType layerType;
    private Renderable renderable;

    public RenderableLayerTypeCombo(Renderable r, LayerType type){

        renderable = r;
        layerType = type;

    }

    public LayerType getType(){return layerType;}
    public Renderable getObj(){return renderable;}

}
