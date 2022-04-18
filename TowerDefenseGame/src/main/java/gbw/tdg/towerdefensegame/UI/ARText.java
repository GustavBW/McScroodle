package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ARText extends RText implements Renderable {

    private double rendPrio,sizeX,sizeY,cornerWidth;
    private Color background = Color.BLACK;

    private ARText(String text, Point2D position, double dropShadowOffset, Font font, double rendPrio){
        super(text,position,dropShadowOffset,Color.WHITE,font);
        this.rendPrio = rendPrio;
    }

    public static ARText create(String text, Point2D position, double dropShadowOffset, double rendPrio){
        return new ARText(text,position,dropShadowOffset,Font.font("Verdana",20),rendPrio);
    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);
        gc.setFill(background);
        gc.fillRoundRect(position.getX(),position.getY(),sizeX,sizeY,cornerWidth,cornerWidth);
    }

    public ARText setTextColor(Color newColor){
        super.textColor = newColor;
        return this;
    }
    public ARText setBackgroundColor(Color newColor){
        this.background = newColor;
        return this;
    }
    public ARText setFont(Font font){
        super.font = font;
        return this;
    }
    public ARText setSize(Point2D dim){
        this.sizeX = dim.getX();
        this.sizeY = dim.getY();
        return this;
    }
    public ARText setCornerWidth(double width){
        this.cornerWidth = width;
        return this;
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
    }

    @Override
    public double getRenderingPriority() {
        return rendPrio;
    }

    @Override
    public void setRenderingPriority(double newPrio) {
        this.rendPrio = newPrio;
    }

    @Override
    public void setDimensions(Point2D dim) {

    }
}
