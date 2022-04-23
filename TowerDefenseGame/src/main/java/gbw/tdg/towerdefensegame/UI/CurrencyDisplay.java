package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Decimals;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.UI.Screens.InGameScreen;
import gbw.tdg.towerdefensegame.Updating;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.List;

public class CurrencyDisplay extends UpdatingMenu<ARText> {

    private final ARText gold,souls;
    private final double textSize = Main.canvasSize.getX() * 0.015;

    public CurrencyDisplay(Point2D pos, Point2D dim, double rendPrio, int coloumns, int rows) {
        super(pos, dim, rendPrio, coloumns, rows);
        this.gold = ARText.create(Main.getGold() + "G",Point2D.ZERO,3,rendPrio)
                .setTextColor(InGameScreen.goldColor)
                .setFont(Font.font("Impact", textSize));
        this.souls = ARText.create(Main.getSouls() + "S",Point2D.ZERO,3,rendPrio)
                .setTextColor(InGameScreen.soulColor)
                .setFont(Font.font("Impact", textSize));
        add(gold);
        add(souls);
    }

    @Override
    public void onUpdate(){
        gold.setText((int) (Main.getGold()) + "G");
        souls.setText((int) (Main.getSouls()) + "S");
    }

    @Override
    public void spawn(){
        super.spawn();
        gold.spawn();
        souls.spawn();
    }

    @Override
    public void destroy(){
        super.destroy();
        gold.destroy();
        souls.destroy();
    }
}
