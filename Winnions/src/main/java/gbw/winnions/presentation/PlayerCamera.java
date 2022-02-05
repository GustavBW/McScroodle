package gbw.winnions.presentation;

import gbw.winnions.domain.Player;
import gbw.winnions.Game;
import gbw.winnions.domain.Tickable;
import javafx.geometry.Point2D;

public class PlayerCamera implements Tickable {

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
            position = new Point2D(localPlayer.getPosition().getX() - Game.screenDimensions.getX() / 2, localPlayer.getPosition().getY() - Game.screenDimensions.getY() / 2 );
        }
    }



    public Player getLocalPlayer(){return localPlayer;}
    public Point2D getPosition(){return position;}
    public boolean toggleLock(){
        lockedOnPlayer = !lockedOnPlayer;
        System.out.println("Locked on player: " + lockedOnPlayer);
        return lockedOnPlayer;
    }


}
