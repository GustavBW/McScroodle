package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.backend.Point2G;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OnScreenDamageNumber extends OnScreenWarning{

    private static final Font defFont = Font.font("Times New Roman", FontWeight.BOLD, 40 * Main.scale.getX());

    public OnScreenDamageNumber(String txt, Point2D pos, int duration) {
        super(txt, pos, duration, Color.RED,defFont);
    }

}
