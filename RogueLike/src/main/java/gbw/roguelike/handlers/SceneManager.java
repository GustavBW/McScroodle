package gbw.roguelike.handlers;

import gbw.roguelike.enums.SceneType;
import gbw.roguelike.interfaces.Renderable;
import gbw.roguelike.ui.GScene;
import gbw.roguelike.ui.StartMenuScene;
import javafx.scene.canvas.GraphicsContext;

public class SceneManager implements Renderable {

    private static SceneType currentSceneType = SceneType.VOID;
    private static GScene currentScene;

    public SceneManager(){
        currentScene = new StartMenuScene();
    }

    @Override
    public void render(GraphicsContext gc) {
        currentScene.render(gc);
    }

    public static boolean changeScene(GScene scene){
        if(scene.getType() != currentSceneType){

            currentScene.deactivateScene();

            currentScene = scene;
            currentScene.activateScene();
            currentSceneType = scene.getType();
            System.out.println(scene);
            return true;
        }
        return false;
    }


}
