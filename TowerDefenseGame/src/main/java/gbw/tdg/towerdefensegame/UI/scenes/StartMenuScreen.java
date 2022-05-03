package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.GameState;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.AssociatedButton;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class StartMenuScreen extends GScene {

    private final Button startGameButton,invocationsButton, augmentsButton;
    private final RText titleText;
    private final Font titleFont = Font.font("Impact", Main.canvasSize.getX() * 0.06);
    private final Font playFont = Font.font("Impact", Main.canvasSize.getX() * 0.05);
    private final Font invoFont = Font.font("Impact", Main.canvasSize.getX() * 0.03);

    public StartMenuScreen(){
        super(75);
        Point2D sGBPos = new Point2D(Main.canvasSize.getX() * 0.4,Main.canvasSize.getY() * 0.65);
        Point2D sGBDim = new Point2D(Main.canvasSize.getX() * 0.2,Main.canvasSize.getY() * 0.1);
        RText sGBText = new RText("PLAY", Point2D.ZERO,5, Color.WHITE, playFont);
        RText invoBText = new RText("Invocations", Point2D.ZERO,5, Color.WHITE, invoFont);
        RText augBText = new RText("Augments", Point2D.ZERO,5, Color.WHITE, invoFont);


        double titleWidth = TextFormatter.getWidthOf("Σ TOWER DEFENCE", titleFont);
        Point2D titlePos = new Point2D((Main.canvasSize.getX() - titleWidth) * .6, Main.canvasSize.getY() * 0.15);
        titleText = new RText("Σ TOWER DEFENCE", titlePos, 20, Color.WHITE, titleFont);

        this.startGameButton = new Button(
                sGBPos, sGBDim.getX(),sGBDim.getY(),sGBText,true
        ){
            @Override
            public void onClick(MouseEvent event){
                Main.onGameStart();
            }
        }.setTextAlignments(0.5,0);

        this.invocationsButton = new Button(
                sGBPos.add(0,sGBDim.getY()), sGBDim.getX(),sGBDim.getY() * .75, invoBText, null, true
        ){
            @Override
            public void onClick(MouseEvent event){
                Main.setState(GameState.INVOCATION_MANAGEMENT);
            }
        }.setTextAlignments(0.5,0);

        this.augmentsButton = new Button(
                invocationsButton.getPosition().add(0,invocationsButton.getDimensions().getY()),sGBDim.getX(),sGBDim.getY() * .75, augBText, null, true
        ){
            @Override
            public void onClick(MouseEvent event){
                Main.setState(GameState.GAME_OVER);
            }
        }.setTextAlignments(0.5,0.3);

        super.group.is(new ArrayList<>(List.of(
                startGameButton,
                invocationsButton,
                augmentsButton
            )
        ));
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        titleText.render(gc);
    }
}
