package gbw.winnions.winnions;

import javafx.geometry.Point2D;

public class PlayerCamera implements Tickable{

    private Point2D position;
    private Player localPlayer;
    private boolean lockedOnPlayer = true;

    public PlayerCamera(Player p, Point2D pos){

        this.position = pos;
        this.localPlayer = p;

    }


    @Override
    public void tick() {

        if(!lockedOnPlayer){

        }else{
            position = localPlayer.getPosition();
        }
    }



    public Player getLocalPlayer(){return localPlayer;}
    public Point2D getPosition(){return position;}
    public boolean toggleLock(){
        lockedOnPlayer = !lockedOnPlayer;
        return lockedOnPlayer;
    }


}
