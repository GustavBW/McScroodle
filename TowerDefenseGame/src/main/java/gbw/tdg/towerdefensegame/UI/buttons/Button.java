package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button implements Clickable, Renderable {

    private static final double renderingPriority = 85D;
    protected Point2D position,origin;
    protected final double sizeX, sizeY;
    protected final RText text;
    private Color backGroundColor, rimColor;

    public Button(Point2D position, double sizeX, double sizeY, RText textUnit){
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = textUnit;
        this.origin = position.subtract(sizeX / 2, sizeY / 2);
        rimColor = new Color(1,1,1,1);
        backGroundColor = new Color(0,0,0,0.5);
    }

    public Button(Point2D position, double sizeX, double sizeY){
        this(position,sizeX,sizeY,new RText("",new Point2D(0,0), 0, Color.BLACK, Font.font("Impact")));
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(backGroundColor);
        gc.fillRect(position.getX() - sizeX / 2, position.getY() - sizeY / 2, sizeX, sizeY);

        gc.setFill(rimColor);
        gc.fillRect(position.getX() - (sizeX / 2) -5, position.getY() - (sizeY / 2) -5, sizeX +10, sizeY +10);

        gc.setFill(Color.BLACK);
        gc.fillRect(position.getX() - sizeX / 2, position.getY() - sizeY / 2, sizeX, sizeY);

        text.render(gc);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        //System.out.println("Checking isInBounds() with pos " + pos.getX() + " " +pos.getY());
        return (pos.getX() < origin.getX() + sizeX && pos.getX() > origin.getX()) && (pos.getY() < origin.getY() + sizeY && pos.getY() > origin.getY());
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void spawn() {
        Clickable.newborn.add(this);
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Clickable.expended.add(this);
        Renderable.expended.add(this);
    }

    @Override
    public void deselect(){

    }

}
