package gbw.roguelike.ui;

import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class TextElement implements Renderable {

    private String text;
    private Point2D pos;
    private Color color;
    private Font font;
    private final double dropShadowOffset = 0.1;

    public TextElement(String text, Font font, Color color){
        this.pos = new Point2D(0,0);
        this.text = text;
        this.font = font;
        this.color = color;
    }

    public TextElement(String text){
        this(
                text,
                Font.font("Helvetica", 20.00),
                Color.WHITE
        );
    }

    public TextElement(String text, Point2D pos){
        this(text);
        this.pos = pos;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFont(font);

        gc.setFill(Color.BLACK);    //Dropshadow
        gc.fillText(text, pos.getX() - (font.getSize() * dropShadowOffset), pos.getY() - (font.getSize() * dropShadowOffset));

        gc.setFill(color);          //Actual text
        gc.fillText(text, pos.getX(), pos.getY());
    }

    public void setPosition(Point2D position){
        this.pos = position;
    }

    public void setText(String s){
        this.text = s;
    }
}
