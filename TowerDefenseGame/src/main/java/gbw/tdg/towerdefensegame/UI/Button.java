package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Destroyable;
import gbw.tdg.towerdefensegame.Main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button implements Clickable, Destroyable {

    private final Point2D position,origin;
    private final double sizeX, sizeY;
    private final RText text;
    private Color backGroundColor, rimColor;

    public Button(Point2D position, double sizeX, double sizeY, RText textUnit){
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = textUnit;
        this.origin = position.subtract(sizeX / 2, sizeY / 2);
        rimColor = new Color(1,1,1,1);
        backGroundColor = new Color(0,0,0,0.5);
        Main.addClickable.add(this);
    }

    public Button(Point2D position, double sizeX, double sizeY){
        this(position,sizeX,sizeY,new RText("",new Point2D(0,0), 0, Color.BLACK, Font.font("Impact")));
    }

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
    public boolean isInBounds(Point2D pos) {
        //System.out.println("Checking isInBounds() with pos " + pos.getX() + " " +pos.getY());
        return (pos.getX() < origin.getX() + sizeX && pos.getX() > origin.getX()) && (pos.getY() < origin.getY() + sizeY && pos.getY() > origin.getY());
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void destroy() {
        Main.removeClickable.remove(this);
    }

    @Override
    public void reInstantiate() {
        Main.addClickable.add(this);
    }
}
