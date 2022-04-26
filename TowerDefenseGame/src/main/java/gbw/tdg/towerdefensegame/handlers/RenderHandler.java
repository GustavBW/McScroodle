package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class RenderHandler {

    private List<Renderable> currentFrame;

    public void render(GraphicsContext gc){
        cleanUp();
        getDrawOrder();

        for(Renderable r : currentFrame){
            r.render(gc);
        }

    }

    private void getDrawOrder(){
        currentFrame = new LinkedList<>(Renderable.active);
        currentFrame.sort(Comparator.comparingDouble(Renderable::getRenderingPriority));
    }

    private void cleanUp(){
        Renderable.active.addAll(Renderable.newborn);
        Renderable.active.removeAll(Renderable.expended);
        Renderable.newborn.clear();
        Renderable.expended.clear();
    }

    private void printFullFrame(){
        System.out.println("_______________________________________________________");
        for(int i = 0; i < currentFrame.size(); i++){
            String objName = currentFrame.get(i).toString();
            int index = Math.min(objName.length(), 30);
            String modObjName = objName.substring(objName.length() - index);
            System.out.println("-"+i+"-||: " + modObjName + " PRIO " + currentFrame.get(i).getRenderingPriority());
        }
        System.out.println("_______________________________________________________");
    }

}
