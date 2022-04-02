package gbw.tdg.towerdefensegame.handlers;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class RenderHandler {

    private List<Renderable> currentFrame = new LinkedList<>();

    public void render(GraphicsContext gc){
        cleanUp();
        getDrawOrder();

        for(Renderable r : currentFrame){
            r.render(gc);
        }

    }

    private void getDrawOrder(){

        List<Renderable> copyOfNewborn = new ArrayList<>(Renderable.newborn);
        int currentFrameSize = currentFrame.size();

        TreeSet<Renderable> treeSet = new TreeSet<>(Comparator.comparingDouble(Renderable::getRenderingPriority));

        for(Renderable r : copyOfNewborn){
            currentFrameSize = currentFrame.size();
            double rPrio = r.getRenderingPriority();
            int optimalIndex = (int) ((currentFrameSize) * (rPrio / 100.00));

            if(currentFrameSize <= optimalIndex){
                currentFrame.add(r);

            }else if(currentFrame.get(optimalIndex) != null){
                double foundPrio = currentFrame.get(optimalIndex).getRenderingPriority();

                if(foundPrio <= rPrio){
                    currentFrame.add(optimalIndex + 1, r);
                }else{

                    while(foundPrio > rPrio && optimalIndex > 0){
                        optimalIndex--;
                        foundPrio = currentFrame.get(optimalIndex).getRenderingPriority();
                    }
                    currentFrame.add(optimalIndex + 1, r);
                }
            }else{
                currentFrame.add(optimalIndex, r);
            }
        }

        Renderable.newborn.clear();
    }

    private void cleanUp(){
        List<Renderable> copyOfExpended = new ArrayList<>(Renderable.expended);
        currentFrame.removeAll(copyOfExpended);
        Renderable.expended.clear();
    }

}
