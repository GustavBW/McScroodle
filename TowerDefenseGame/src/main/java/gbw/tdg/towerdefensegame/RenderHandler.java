package gbw.tdg.towerdefensegame;

import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class RenderHandler {

    private List<Renderable> currentFrame = new LinkedList<>();

    public void render(GraphicsContext gc){
        gatherDrawCall();

        for(Renderable r : currentFrame){
            r.render(gc);
        }

    }

    private void gatherDrawCall(){
        cleanUp();

        List<Renderable> copyOfNewborn = new ArrayList<>(Renderable.newborn);
        int currentFrameSize = currentFrame.size();

        for(Renderable r : copyOfNewborn){
            System.out.println("Finding index for " + r + " with prio " + r.getRenderingPriority());
            currentFrameSize = currentFrame.size();
            double rPrio = r.getRenderingPriority();
            int matchingIndex = (int) ((currentFrameSize) * (rPrio / 100.00));

            if(currentFrameSize <= matchingIndex){
                currentFrame.add(r);

            }else if(currentFrame.get(matchingIndex) != null){
                double foundPrio = currentFrame.get(matchingIndex).getRenderingPriority();

                if(foundPrio <= rPrio){
                    currentFrame.add(matchingIndex + 1, r);
                }else{

                    while(foundPrio > rPrio && matchingIndex > 0){
                        matchingIndex--;
                        foundPrio = currentFrame.get(matchingIndex).getRenderingPriority();
                    }
                    currentFrame.add(matchingIndex + 1, r);
                }
            }
            System.out.println("Gave index: " + matchingIndex);
        }

        Renderable.newborn.clear();
    }

    private void cleanUp(){
        List<Renderable> copyOfExpended = new ArrayList<>(Renderable.expended);
        currentFrame.removeAll(copyOfExpended);
        Renderable.expended.clear();
    }

}
