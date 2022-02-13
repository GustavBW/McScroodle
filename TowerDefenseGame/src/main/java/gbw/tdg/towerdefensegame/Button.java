package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button implements Clickable{

    private final Point2D position;
    private final double sizeX, sizeY;
    private final RText text;
    private Color backGroundColor, rimColor;

    public Button(Point2D position, double sizeX, double sizeY, RText textUnit){
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = textUnit;
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
        return (pos.getX() < position.getX() + sizeX && pos.getX() > position.getX()) && (pos.getY() < position.getY() + sizeY && pos.getY() > position.getY());
    }

    @Override
    public void onInteraction() {
        System.out.println("You clicked a Button");
    }
}
