package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.TowerFunctionsDisplay;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.handlers.ClickListener;
import gbw.tdg.towerdefensegame.handlers.MouseHandler;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MoveTowerButton extends Button implements Tickable, ClickListener {

    private static final Point2D textOffset = new Point2D(0,0);
    private final TowerFunctionsDisplay display;
    private Tower towerToMove = null;

    public MoveTowerButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO, 0,0,new RText(
             "Move",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ),true);
        display = towerFunctionsDisplay;
        super.setRimColor(Color.TRANSPARENT);
    }

    @Override
    public void onInteraction(){
        towerToMove = display.getTower();
        towerToMove.setActive(false);
        ClickListener.newborn.add(this);
        display.onTowerMove();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);

        if(towerToMove != null) {
            Point2D tPos = towerToMove.getPosition().add(towerToMove.getDimensions().multiply(0.5));
            gc.setFill(Color.BLACK);
            gc.strokeLine(tPos.getX(), tPos.getY(), MouseHandler.mousePos.getX(),MouseHandler.mousePos.getY());
        }
    }

    @Override
    public void trigger(MouseEvent event) {
        towerToMove.setPosition(MouseHandler.mousePos.subtract(towerToMove.getDimensions().multiply(0.5)));
        towerToMove.setActive(true);
        towerToMove = null;
        ClickListener.expended.add(this);
    }

    @Override
    public void spawn(){
        super.spawn();
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy(){
        super.destroy();
        Tickable.expended.add(this);
    }

    @Override
    public Clickable getRoot(){
        return display.getTower();
    }
}
