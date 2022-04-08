package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.ProgressBar;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.WaveRoundDisplay;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.TowerShopButton;
import gbw.tdg.towerdefensegame.handlers.WaveManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InGameScreen extends GScene implements Tickable, IRenderableOwner {

    private final ProgressBar mainHealthBar;
    private final RText goldDisplay;
    private final Button towerShopButton;
    private final WaveRoundDisplay roundDisplay;
    private final static Color goldColor = new Color(255 / 255.0,178 / 255.0,0,1);
    private final Point2D roundDisplayDim = new Point2D(Main.canvasSize.getX() * 0.1, Main.canvasSize.getY() * 0.05);

    public InGameScreen(WaveManager waveManager){
        super(75);
        mainHealthBar = new FancyProgressBar(
                Main.canvasSize.getX() - 10, 50,
                new Point2D(0,0), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1),
                new Color(255 / 255.0,69 / 255.0,0,1));
        roundDisplay = new WaveRoundDisplay(waveManager,
                new Point2D(Main.canvasSize.getX() * 0.5 - (roundDisplayDim.getX() * 0.5), 0),
                roundDisplayDim);
        goldDisplay = new RText("" + Main.getGold(),
                new Point2D(Main.canvasSize.getX() * 0.5, Main.canvasSize.getY()*0.06),
                3, goldColor,
                Font.font("Impact", 55));
        Point2D towerShopPos = new Point2D(Main.canvasSize.getX() * 0.90,Main.canvasSize.getY() * 0.95);
        towerShopButton = new TowerShopButton(towerShopPos, Main.canvasSize.getX() *0.1,
                Main.canvasSize.getY() *0.05,
                new RText("Towers",towerShopPos.subtract(10,0),3,goldColor,Font.font("Impact",35)));
        towerShopButton.setPosition(towerShopButton.getPosition().subtract(towerShopButton.getDimensions().multiply(0.5)));
    }

    @Override
    public void render(GraphicsContext gc) {
        goldDisplay.render(gc);
    }

    @Override
    public void tick(){
        mainHealthBar.setVal((double) Main.HP / Main.MAXHP);
        goldDisplay.setText("" + (int) (Main.getGold()));
    }

    @Override
    public void spawn() {
        super.spawn();
        Tickable.newborn.add(this);
        mainHealthBar.spawn();
        towerShopButton.spawn();
        roundDisplay.spawn();
    }

    @Override
    public void destroy() {
        super.destroy();
        Tickable.expended.add(this);
        mainHealthBar.destroy();
        towerShopButton.destroy();
        roundDisplay.destroy();
    }

}
