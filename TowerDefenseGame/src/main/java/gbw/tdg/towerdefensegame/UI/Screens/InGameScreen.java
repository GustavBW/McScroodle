package gbw.tdg.towerdefensegame.UI.Screens;

import gbw.tdg.towerdefensegame.*;
import gbw.tdg.towerdefensegame.UI.*;
import gbw.tdg.towerdefensegame.UI.buttons.AugmentShopButton;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.TowerShopButton;
import gbw.tdg.towerdefensegame.handlers.WaveManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InGameScreen extends GScene implements Tickable, IRenderableOwner {

    private final ProgressBar mainHealthBar;
    private final Menu<ARText> currencyDisplay;
    private final Button towerShopButton,augmentShopButton;
    private final WaveRoundDisplay roundDisplay;
    public final static Color goldColor = new Color(255 / 255.0,178 / 255.0,0,1);
    public final static Color soulColor = new Color(0,199 / 255.0,199 / 255.0,1);
    private final Point2D roundDisplayDim = new Point2D(Main.canvasSize.getX() * 0.1, Main.canvasSize.getY() * 0.05);

    public InGameScreen(WaveManager waveManager){
        super(75);
        mainHealthBar = new FancyProgressBar(
                Main.canvasSize.getX() - 10, Main.canvasSize.getY() * 0.02629,
                new Point2D(0,0), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1),
                new Color(255 / 255.0,69 / 255.0,0,1));
        roundDisplay = new WaveRoundDisplay(waveManager,
                new Point2D(Main.canvasSize.getX() * 0.5 - (roundDisplayDim.getX() * 0.5), 0),
                roundDisplayDim);
        Point2D towerShopPos = new Point2D(Main.canvasSize.getX() * 0.90,Main.canvasSize.getY() * 0.95);
        towerShopButton = new TowerShopButton(towerShopPos, Main.canvasSize.getX() *0.1,
                Main.canvasSize.getY() *0.05,
                new RText("Towers",towerShopPos.subtract(10,0),3,goldColor,Font.font("Impact",Main.canvasSize.getX() * 0.0182)));
        towerShopButton.setPosition(towerShopButton.getPosition().subtract(towerShopButton.getDimensions().multiply(0.5)));

        Point2D augShopDim = new Point2D(Main.canvasSize.getX() *0.1, Main.canvasSize.getY() *0.05);
        augmentShopButton = new AugmentShopButton(towerShopPos.subtract(0, augShopDim.getY()), augShopDim.getX(), augShopDim.getY());
        augmentShopButton.setPosition(augmentShopButton.getPosition().subtract(augmentShopButton.getDimensions().multiply(0.5)));

        currencyDisplay = new CurrencyDisplay(
                roundDisplay.getPosition().add(0,roundDisplayDim.getY() * 1.9),roundDisplayDim,75,2,1
        );

    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void tick(){
        mainHealthBar.setVal((double) Main.HP / Main.MAXHP);
    }

    @Override
    public void spawn() {
        super.spawn();
        Tickable.newborn.add(this);
        mainHealthBar.spawn();
        towerShopButton.spawn();
        roundDisplay.spawn();
        augmentShopButton.spawn();
        currencyDisplay.spawn();
    }

    @Override
    public void destroy() {
        super.destroy();
        Tickable.expended.add(this);
        mainHealthBar.destroy();
        towerShopButton.destroy();
        roundDisplay.destroy();
        augmentShopButton.destroy();
        currencyDisplay.destroy();
    }

}
