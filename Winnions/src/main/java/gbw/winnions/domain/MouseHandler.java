package gbw.winnions.domain;

import gbw.winnions.presentation.PlayerCamera;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseHandler implements EventHandler<MouseEvent> {

    private Player localPlayer;
    private PlayerCamera localPlayerCamera;

    public MouseHandler(Player p1, PlayerCamera pc){
        this.localPlayer = p1;
        this.localPlayerCamera = pc;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY){
            localPlayer.abilityShoot(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
        }
    }
}
