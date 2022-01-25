package gbw.winnions.winnions;

import javafx.geometry.Point2D;

public class PlayerCamera implements Tickable{

    private Point2D position;
    private Player localPlayer;
    private boolean lockedOnPlayer = true;

    public PlayerCamera(Player p){

        this.position = p.getPosition();
        this.localPlayer = p;

    }


    @Override
    public void tick() {

        if(!lockedOnPlayer){

        }else{
            position = new Point2D(localPlayer.getPosition().getX() - Game.gameDimensions.getX() / 2, localPlayer.getPosition().getY() - Game.gameDimensions.getY() / 2 );
        }
    }



    public Player getLocalPlayer(){return localPlayer;}
    public Point2D getPosition(){return position;}
    public boolean toggleLock(){
        lockedOnPlayer = !lockedOnPlayer;
        return lockedOnPlayer;
    }


}
