package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.GraphicalInventory;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.SmallDisplayableDisplay;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.handlers.UIController;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class AugmentManagementScene extends GScene {

    private final Button backButton;
    private final GraphicalInventory<SmallDisplayableDisplay<Augment>> augMenu;
    private final List<Augment> augments = Augment.getAugs();
    private final double offset15 = Main.scale.getX() * 15, offset100 = Main.scale.getX() * 100;
    private final Point2D menuPos = new Point2D(0, Main.canvasSize.getY() * 0.1);
    private final Point2D menuDim = new Point2D(Main.canvasSize.getX() * 0.75, Main.canvasSize.getY() * 0.95);
    private final Point2D shardsPos = new Point2D(Main.canvasSize.getX() * .5, 0);
    private final Point2D shardsDim = new Point2D(0, Main.canvasSize.getY() * 0.1);
    private final Point2D backButtonPos = new Point2D(menuDim.getX() + menuPos.getX() + offset15,Main.canvasSize.getY() - shardsDim.getY() + offset15);
    private final Point2D backButtonDim = Main.canvasSize.subtract(backButtonPos.add(2 * offset15,2 * offset15));

    public AugmentManagementScene() {
        super(0);

        this.augMenu = new GraphicalInventory<>(5,3,menuPos,menuDim,offset15,70);

        for(Augment a : augments){
            SmallDisplayableDisplay<Augment> sDD = new SmallDisplayableDisplay<>(Point2D.ZERO,new Point2D(400,400),null,true,a);
            augMenu.add(sDD);
        }

        backButton = new Button(ContentEngine.BUTTONS.getImage("ArrowedButtonLeftFillBlack"),backButtonPos,backButtonDim,
                new RText("Back", Point2D.ZERO,8, Color.WHITE, Font.font("Impact", 100 * Main.scale.getX())),null){
            @Override
            public void onClick(MouseEvent event){
                Main.setState(UIController.getPrevious());
            }
        }.setShouldRenderBackground(true).setTextAlignments(0.5,0.3);

        super.group.is(List.of(
                backButton,
                augMenu
        ));
    }


}
