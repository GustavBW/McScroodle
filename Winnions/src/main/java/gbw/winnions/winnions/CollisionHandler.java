package gbw.winnions.winnions;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler implements Runnable{

    private static List<Collidable> collidables = new ArrayList<>();
    private static List<Collidable> addNextPass = new ArrayList<>();
    private static List<Collidable> removeNextPass = new ArrayList<>();

    @Override
    public void run() {

        while(Game.isRunning){


            for(Collidable c : collidables){
                for(Collidable n : collidables){
                    if(n != c && c.isInBounds(n.getPosition())) {
                        c.onCollision(n);
                        n.onCollision(c);
                    }
                }
            }

            updateCollidables();
        }
    }

    private void updateCollidables(){
        collidables.addAll(addNextPass);
        collidables.removeAll(removeNextPass);

        addNextPass.clear();
        removeNextPass.clear();
    }

    public static void addCollidable(Collidable c){
        addNextPass.add(c);
    }
    public static void addAllCollidable(List<Collidable> list){
        for(Collidable c : list){
            addCollidable(c);
        }
    }
    public static void removeCollidable(Collidable c){
        removeNextPass.add(c);
    }
    public static void removeAllCollidable(List<Collidable> list){
        for(Collidable c : list){
            removeCollidable(c);
        }
    }
}
