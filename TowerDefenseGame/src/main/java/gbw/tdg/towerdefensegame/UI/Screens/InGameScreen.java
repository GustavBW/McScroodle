package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.GScene;
import gbw.tdg.towerdefensegame.UI.ProgressBar;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.TowerShopButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InGameScreen extends GScene implements Tickable, IRenderableOwner {

    private static final double renderingPriority = 75D;
    private final ProgressBar mainHealthBar;
    private final RText goldDisplay;
    private final Button towerShopButton;
    private final static Color goldColor = new Color(255 / 255.0,178 / 255.0,0,1);

    public InGameScreen(){
        mainHealthBar = new FancyProgressBar(
                Main.canvasSize.getX() - 10, 50,
                new Point2D(0,0), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1),
                new Color(255 / 255.0,69 / 255.0,0,1));
        goldDisplay = new RText("" + Main.getGold(),
                new Point2D(Main.canvasSize.getX() * 0.49, Main.canvasSize.getY()*0.04),
                3, goldColor,
                Font.font("Impact", 55));
        Point2D towerShopPos = new Point2D(Main.canvasSize.getX() * 0.90,Main.canvasSize.getY() * 0.95);
        towerShopButton = new TowerShopButton(towerShopPos, Main.canvasSize.getX() *0.1,
                Main.canvasSize.getY() *0.05,
                new RText("Towers",towerShopPos.subtract(10,0),3,goldColor,Font.font("Impact",35)));
    }

    @Override
    public void render(GraphicsContext gc) {
        goldDisplay.render(gc);
    }
    @Override
    public void tick(){
        mainHealthBar.setVal((double) Main.HP / Main.MAXHP);
        goldDisplay.setText("" + Main.getGold());
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(0,0);
    }

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
        mainHealthBar.spawn();
        towerShopButton.spawn();
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
        mainHealthBar.destroy();
        towerShopButton.destroy();
    }

}
