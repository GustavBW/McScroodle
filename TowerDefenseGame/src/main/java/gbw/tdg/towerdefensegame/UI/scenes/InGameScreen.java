package gbw.tdg.towerdefensegame.UI.scenes;

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

import java.util.List;

public class InGameScreen extends GScene implements Tickable, IRenderableOwner {

    private final ProgressBar mainHealthBar;
    private final Menu<ARText> currencyDisplay;
    private final Button towerShopButton,augmentShopButton;
    private final WaveRoundDisplay roundDisplay;
    public final static Color goldColor = new Color(255 / 255D,200/255D,100/255D,1);
    public final static Color goldColor2 = new Color(255 / 255.0,178 / 255.0,0,1);
    public final static Color soulColor = new Color(0,199 / 255.0,199 / 255.0,1);
    private final Point2D roundDisplayDim = new Point2D(Main.canvasSize.getX() * 0.1, Main.canvasSize.getY() * 0.05);
    private final Font towerShopFont = Font.font("Impact",Main.canvasSize.getX() * 0.0182);

    public static final OnScreenLog informationLog = new OnScreenLog(new Point2D(0,Main.canvasSize.getY() * 3/4D),new Point2D(Main.canvasSize.getX(),0));
    public static final OnScreenLog errorLog = new OnScreenLog(new Point2D(0,Main.canvasSize.getY() * 1/4D),new Point2D(Main.canvasSize.getX(),0));

    public InGameScreen(WaveManager waveManager){
        super(75);
        mainHealthBar = new FancyProgressBar(
                Main.canvasSize.getX() - 10, Main.canvasSize.getY() * 0.02629,
                new Point2D(0,0), new Color(31 / 255.0,122 / 255.0,4 / 255.0,1),
                new Color(255 / 255.0,69 / 255.0,0,1));
        mainHealthBar.setRenderingPriority(80);
        roundDisplay = new WaveRoundDisplay(waveManager,
                new Point2D(Main.canvasSize.getX() * 0.5 - (roundDisplayDim.getX() * 0.5), 0),
                roundDisplayDim);

        Point2D towerShopPos = new Point2D(Main.canvasSize.getX() * .95,Main.canvasSize.getY() * 0.95);
        Point2D towerShopDim = new Point2D(Main.canvasSize.getX() *0.1, Main.canvasSize.getY() *0.05);

        towerShopButton = new TowerShopButton(towerShopPos, towerShopDim.getX(),
                towerShopDim.getY(),
                new RText("Towers",Point2D.ZERO,3,goldColor,towerShopFont));
        towerShopButton.setPosition(towerShopButton.getPosition().subtract(towerShopButton.getDimensions().multiply(0.5)));
        towerShopButton.setTextAlignments(0.5,0.3);


        augmentShopButton = new AugmentShopButton(towerShopPos.subtract(0, towerShopDim.getY()), towerShopDim.getX(), towerShopDim.getY(), goldColor,towerShopFont);
        augmentShopButton.setPosition(augmentShopButton.getPosition().subtract(augmentShopButton.getDimensions().multiply(0.5)));
        augmentShopButton.setTextAlignments(0.5,0.3);

        currencyDisplay = new CurrencyDisplay(
                roundDisplay.getPosition().add(roundDisplayDim.getX() / 8,roundDisplayDim.getY() * 1.9),roundDisplayDim,75,2,1
        );


        super.group.is(List.of(
                mainHealthBar,
                towerShopButton,
                roundDisplay,
                augmentShopButton,
                currencyDisplay,
                informationLog,
                errorLog
        ));
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
    }

    @Override
    public void destroy() {
        super.destroy();
        Tickable.expended.add(this);
    }

}
