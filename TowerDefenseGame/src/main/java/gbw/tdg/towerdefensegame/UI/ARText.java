package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ARText extends RText implements Renderable {

    private double rendPrio,sizeX,sizeY,cornerWidth;
    private Color background = Color.BLACK;
    private boolean renderBackground = false;

    protected ARText(String text, Point2D position, double dropShadowOffset, Font font, double rendPrio){
        super(text,position,dropShadowOffset,Color.WHITE,font);
        this.rendPrio = rendPrio;
        this.sizeX = TextFormatter.getWidthOf(text,font);
        this.sizeY = font.getSize();
    }

    public static ARText create(String text, Point2D position, double dropShadowOffset, double rendPrio){
        return new ARText(text,position,dropShadowOffset,Font.font("Verdana",20),rendPrio);
    }

    @Override
    public void render(GraphicsContext gc){
        if(renderBackground) {
            gc.setFill(background);
            gc.fillRoundRect(position.getX(), position.getY() - super.getFont().getSize(), sizeX, sizeY, cornerWidth, cornerWidth);
        }
        super.render(gc);
    }

    public ARText setTextColor2(Color newColor){
        super.textColor = newColor;
        return this;
    }
    public Color getTextColor(){
        return super.textColor;
    }
    public Font getFont(){
        return super.font;
    }
    public ARText setRenderBackground(boolean state){
        this.renderBackground = state;
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
        super.setMaxTextWidth(dim.getX());
    }

    public ARText setDimAR(Point2D dim) {
        super.setMaxTextWidth(dim.getX());
        return this;
    }

    public Color getBackgroundColor(){return background;}
    @Override
    public Point2D getDimensions() {
        return new Point2D(font.getSize(), 0);
    }

    public ARText copy(){
        return ARText.create(getText(),getPosition(),getDropShadowOffset(),getRenderingPriority())
                .setBackgroundColor(this.getBackgroundColor())
                .setTextColor2(this.getTextColor())
                .setFont(this.getFont());
    }
}
