package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TowerStatDisplay {

    private final RText text;
    private final Tower tower;

    public TowerStatDisplay(Tower t, Point2D position){
        this.text = new RText(t.toString(), position,1, Color.WHITESMOKE, Font.font("Impact", 20));
        this.tower = t;
    }

    public void render(GraphicsContext gc){
        text.render(gc);
    }

    public void setPosition(Point2D p){
        text.setPosition(p);
    }

}
