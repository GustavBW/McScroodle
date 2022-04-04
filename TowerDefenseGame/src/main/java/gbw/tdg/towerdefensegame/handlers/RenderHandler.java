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

        /*
        for(Renderable r : copyOfNewborn){
            currentFrameSize = currentFrame.size();
            double rPrio = r.getRenderingPriority();
            int optimalIndex = (int) ((currentFrameSize) * (rPrio / 100.00));
            int actualIndex = 421;

            if(currentFrameSize <= optimalIndex) {
                currentFrame.add(r);
                actualIndex = 420;

            }else if(rPrio >= currentFrame.get(currentFrameSize - 1).getRenderingPriority()){
                currentFrame.add(r);
                actualIndex = 422;

            }else if(currentFrame.get(optimalIndex) != null){
                double foundPrio = currentFrame.get(optimalIndex).getRenderingPriority();

                if(foundPrio <= rPrio){
                    currentFrame.add(optimalIndex + 1, r);
                    actualIndex = optimalIndex + 1;
                }else{
                    int searchingForIndex = optimalIndex;
                    while(foundPrio > rPrio && searchingForIndex > 0){
                        searchingForIndex--;
                        foundPrio = currentFrame.get(searchingForIndex).getRenderingPriority();
                    }
                    currentFrame.add(optimalIndex + 1, r);
                    actualIndex = searchingForIndex;
                }
            }else{
                currentFrame.add(optimalIndex, r);
                actualIndex = optimalIndex;
            }

            System.out.println(r + " optimal: " + optimalIndex + " actual " + actualIndex);
        }
        */
        currentFrame = Renderable.active;
        currentFrame.sort(Comparator.comparingDouble(Renderable::getRenderingPriority));
        //printFullFrame();
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
