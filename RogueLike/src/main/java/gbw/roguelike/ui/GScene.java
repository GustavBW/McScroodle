package gbw.roguelike.ui;

import gbw.roguelike.enums.SceneType;
import gbw.roguelike.interfaces.Renderable;

public abstract class GScene implements Renderable {

    protected SceneType type = SceneType.VOID;


    public abstract void activateScene();
    public abstract void deactivateScene();

    public SceneType getType(){return type;}

    @Override
    public String toString(){
        return "Scene: " + type;
    }
}
