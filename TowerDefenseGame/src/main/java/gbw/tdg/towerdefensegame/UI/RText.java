package gbw.tdg.towerdefensegame.UI;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.nio.charset.StandardCharsets;

public class RText {

    private String text;
    private Point2D position;
    private final double dropShadowOffset;
    private final Color textColor;
    private final Font font;
    private final Color dropShadowColor;

    public RText(String text, Point2D position, double dropShadowOffset, Color textColor, Font font){
        this.text = text;
        this.position = position;
        this.dropShadowOffset = dropShadowOffset;
        this.textColor = textColor;
        this.font = font;
        this.position = this.position.subtract(font.getSize() * 1.1,(-font.getSize() / 2) * 0.9);
        dropShadowColor = new Color(0,0,0,1);
    }

    public void render(GraphicsContext gc){
        gc.setFont(font);
        gc.setFill(dropShadowColor);
        gc.fillText(text, position.getX() + dropShadowOffset, position.getY() + dropShadowOffset);

        gc.setFill(textColor);
        gc.fillText(text, position.getX(), position.getY());

    }

    public void setText(String s){text = s;}
    public String getText(){return text;}
    public Point2D getPosition(){return position;}
    public void setPosition(Point2D pos){position = pos;}


}
