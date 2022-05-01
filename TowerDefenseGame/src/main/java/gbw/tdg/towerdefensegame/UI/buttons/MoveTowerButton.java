package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.TowerFunctionsDisplay;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.vfx.ConnectingLine;
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
    private boolean moveStarted = false, destroyCalledDuringMove = false, movePending = false;
    private Point2D moveAlongVector = null;
    private Point2D ogTowerPosition;
    private Point2D whereToPosition;
    private Point2D singleMoveStep;
    private Point2D lineToPos;
    private double lengthToMove;
    private long moveStartTime,lastCall;
    private int moveLengthMS = 5_000;

    public MoveTowerButton(TowerFunctionsDisplay towerFunctionsDisplay) {
        super(Point2D.ZERO, 0,0,new RText(
             "Move",Point2D.ZERO,3, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.01)
        ),true);
        display = towerFunctionsDisplay;
        super.setRimColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(MouseEvent event){
        towerToMove = display.getTower();
        towerToMove.setActive(false);
        ogTowerPosition = towerToMove.getPosition();
        ClickListener.newborn.add(this);
        display.onTowerMove();
        movePending = true;
    }

    @Override
    public void tick() {
        lineToPos = MouseHandler.mousePos;

        if(moveStarted){
            lineToPos = whereToPosition;
            towerToMove.setPosition(towerToMove.getPosition().add(singleMoveStep.multiply(System.currentTimeMillis() - lastCall)));

            if(moveStartTime + moveLengthMS <= System.currentTimeMillis()){
                onMoveFinished();
            }
        }
        lastCall = System.currentTimeMillis();
    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);

        if(towerToMove != null) {
            Point2D tPos = towerToMove.getPosition().add(towerToMove.getDimensions().multiply(0.5));
            gc.setFill(Color.BLACK);
            gc.strokeLine(tPos.getX(), tPos.getY(), lineToPos.getX(),lineToPos.getY());
        }
    }

    private void onMoveFinished(){
        towerToMove.setPosition(whereToPosition);
        towerToMove.setActive(true);
        towerToMove = null;
        moveStarted = false;
        movePending = false;
        if(destroyCalledDuringMove){
            destroy();
            destroyCalledDuringMove = false;
        }
    }

    @Override
    public void trigger(MouseEvent event) {
        if(towerToMove != null) {
            whereToPosition = MouseHandler.mousePos.add(towerToMove.getDimensions().multiply(0.5));
            moveAlongVector = whereToPosition.subtract(ogTowerPosition);
            lengthToMove = moveAlongVector.magnitude();
            Point2D moveAlongVectorNorm = moveAlongVector.normalize();
            double howMuchToMoveAMS = lengthToMove / moveLengthMS;
            singleMoveStep = moveAlongVectorNorm.multiply(howMuchToMoveAMS);


            moveStartTime = System.currentTimeMillis();
            moveStarted = true;
            new ConnectingLine(moveLengthMS, display.getRenderingPriority(), towerToMove, whereToPosition).spawn();
        }
        ClickListener.expended.add(this);
    }

    @Override
    public void spawn(){
        super.spawn();
        Tickable.newborn.add(this);
        destroyCalledDuringMove = false;
    }

    @Override
    public void destroy(){
        super.destroy();

        if(movePending) {
            destroyCalledDuringMove = true;
        }else{
            Tickable.expended.add(this);
        }
    }

    @Override
    public Clickable getRoot(){
        return display.getTower();
    }
}
