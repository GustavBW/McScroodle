package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.MenuButton;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class GameOverScreen extends GScene {

    private Button menuButton, youDiedText;
    private final Point2D menuBPos = new Point2D(Main.canvasSize.getX() * 0.4,Main.canvasSize.getY() * 0.65);
    private final Point2D menuBDim = new Point2D(Main.canvasSize.getX() * 0.2,Main.canvasSize.getY() * 0.1);

    public GameOverScreen(){
        super(75);
        background = new Color(0,0,0,0.5);

        RText textUnit = new RText("MENU", Point2D.ZERO,3,new Color(1,1,1,1), Font.font("Impact", Main.canvasSize.getX() * 0.04));
        this.menuButton = new Button(menuBPos, menuBDim.getX(),menuBDim.getY(), textUnit,true){
            @Override
            public void onClick(MouseEvent event){
                Main.onStartMenu();
                Main.onGameOver();
                System.out.println("Menu button pressed");
            }
        }.setTextAlignments(0.5,0.3);

        RText uDT = new RText("YOU DIED", Point2D.ZERO, 8,new Color(1,0,0,1), Font.font("Impact", Main.canvasSize.getX() * 0.1));
        this.youDiedText = new Button(
                new Point2D(Main.canvasSize.getX() * .5, Main.canvasSize.getY() * 0.1), 0,Main.canvasSize.getY() * 0.01,uDT,null, false
        ).setTextAlignments(0.5,0.3);

        group.is(List.of(
                menuButton,
                youDiedText
        ));
    }
}
