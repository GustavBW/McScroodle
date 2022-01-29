package gbw.winnions.domain;

import gbw.winnions.Game;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class TickHandler implements Runnable{

    public static List<Tickable> tickables = new ArrayList<>();
    private static List<Tickable> toAddNextPassList = new ArrayList<>();
    private static List<Tickable> toRemoveNextPassList = new ArrayList<>();

    public TickHandler(){}

    @Override
    public void run() {

        while(Game.isRunning) {
            for (Tickable t : tickables) {
                t.tick();
            }


            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            updateTickables();
        }
    }


    private void updateTickables(){
        tickables.addAll(toAddNextPassList);
        tickables.removeAll(toRemoveNextPassList);

        for(int i = 0; i < 3; i++){
            try {
                toRemoveNextPassList.clear();
                toAddNextPassList.clear();
                break;
            } catch (ConcurrentModificationException e) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void addTickable(Tickable t){toAddNextPassList.add(t);}
    public static void removeTickable(Tickable t){toRemoveNextPassList.add(t);}
}
