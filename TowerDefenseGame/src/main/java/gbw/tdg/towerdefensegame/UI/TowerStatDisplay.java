package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TowerStatDisplay implements Renderable {

    private double renderingPriority = 57;
    private final RText text;
    private final Tower tower;
    private Color background = new Color(0,0,0,0.5);
    private Point2D position;
    private double sizeX,sizeY, cornerWidth = 15;

    public TowerStatDisplay(Tower t, Point2D position){
        this.text = new RText(t.toString(), position.add(30,15),1, Color.WHITESMOKE, Font.font("Impact", 20));
        this.tower = t;
        this.position = position;
        this.sizeX = Main.canvasSize.getX() * 0.1;
        this.sizeY = Main.canvasSize.getY() * 0.1;
    }

    public void render(GraphicsContext gc){
        gc.setFill(background);
        gc.fillRoundRect(position.getX(), position.getY(), sizeX, sizeY,cornerWidth,cornerWidth);

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
    public void setRenderingPriority(double newPrio){this.renderingPriority = newPrio;}
    public void setPosition(Point2D p){
        text.setPosition(p);
    }
    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }
    @Override
    public void spawn() {
        Renderable.newborn.add(this);
    }
    @Override
    public void destroy() {
        Renderable.expended.add(this);
    }

    public Point2D getExtremeties() {
        return new Point2D(
                position.getX() + sizeX,
                position.getY() + sizeY
        );
    }

    public void setText(String newText) {
        text.setText(newText);
    }
}
