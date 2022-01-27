package gbw.winnions.winnions;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler implements Runnable{

    private static double collisionPadding = 0;
    private static List<Collidable> collidables = new ArrayList<>();
    private static List<Collidable> addNextPass = new ArrayList<>();
    private static List<Collidable> removeNextPass = new ArrayList<>();
    private Player localPlayer;

    public CollisionHandler(Player p){
        localPlayer = p;
    }

    @Override
    public void run() {
        while(Game.isRunning){

            for(Collidable c : collidables){
                for(Point2D p : c.getVertexes()) {

                    for (Point2D playerVert : localPlayer.getVertexes()) {

                        Point2D vecToFrom = playerVert.subtract(p);
                        if (vecToFrom.magnitude() <= collisionPadding) {
                            System.out.println("Collision! " + c + " | " + localPlayer);
                            c.onCollision(localPlayer);
                            localPlayer.onCollision(c);
                            break;
                        }
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
