package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.nio.charset.StandardCharsets;

public class RText {

    public static final RText EMPTY = new RText("",new Point2D(0,0),0,Color.BLACK,Font.font("Verdana", 15));
    public static final Font STANDARD_FONT = Font.font("Verdana",20 * Main.scale.getY());
    private String text;
    protected Point2D position;
    private double dropShadowOffset;
    protected Color textColor;
    protected Font font;
    private double defaulWidth;
    protected Color dropShadowColor;

    public RText(String text, Point2D position, double dropShadowOffset, Color textColor, Font font){
        this.text = text;
        this.position = position;
        this.dropShadowOffset = dropShadowOffset;
        this.textColor = textColor;
        this.font = font;
        this.defaulWidth = getDefaulWidth();
        this.position = this.position.subtract(font.getSize() * 1.1,(-font.getSize() / 2) * 0.9);
        dropShadowColor = new Color(0,0,0,1);
    }

    public RText(Point2D p){
        this("",p,1,Color.BLACK,Font.font("Verdana", 20));
    }

    public void render(GraphicsContext gc){
        gc.setFont(font);
        gc.setFill(dropShadowColor);
        gc.fillText(text, position.getX() + dropShadowOffset, position.getY() + dropShadowOffset,  defaulWidth);

        gc.setFill(textColor);
        gc.fillText(text, position.getX(), position.getY(),defaulWidth);

    }

    private double getDefaulWidth(){
        double d = font.getSize() * text.length();
        return d <= 0 ? Main.canvasSize.getX() : d;
    }
    public void setText(String s){
        text = s;
        defaulWidth = getDefaulWidth();
    }

    public RText setFont(Font font){
        this.font = font;
        defaulWidth = getDefaulWidth();
        return this;
    }
    public void setTextColor(Color color){
        this.textColor = color;
    }
    public void setMaxTextWidth(double width){
        defaulWidth = width;
    }
    public Font getFont(){
        return font;
    }
    public String getText(){return text;}
    public Point2D getPosition(){return position;}
    public void setPosition(Point2D pos){position = pos;}
    public double getSize(){
        return font.getSize();
    }
    public double getDropShadowOffset(){
        return dropShadowOffset;
    }
    public void setDropShadowOffset(double offset){
        this.dropShadowOffset = offset;
    }
    public void setDropShadowColor(Color color){
        this.dropShadowColor = color;
    }

}
