package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.GraphicalInventory;
import gbw.tdg.towerdefensegame.UI.OnScreenWarning;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.SmallDisplayableDisplay;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import gbw.tdg.towerdefensegame.handlers.UIController;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class InvocationManagementScene extends GScene{

    private final GraphicalInventory<SmallDisplayableDisplay<Invocation>> invoMenu, upgradeShowcaseMenu;
    private final double offset15 = Main.scale.getX() * 15, offset100 = Main.scale.getX() * 100;
    private final Point2D menuPos = new Point2D(0, Main.canvasSize.getY() * 0.1);
    private final Point2D menuDim = new Point2D(Main.canvasSize.getX() * 0.75, Main.canvasSize.getY() * 0.95);
    private final Point2D shardsPos = new Point2D(Main.canvasSize.getX() * .5, 0);
    private final Point2D shardsDim = new Point2D(0, Main.canvasSize.getY() * 0.1);
    private final Point2D backButtonPos = new Point2D(menuDim.getX() + menuPos.getX() + offset15,Main.canvasSize.getY() - shardsDim.getY() + offset15);
    private final Point2D backButtonDim = Main.canvasSize.subtract(backButtonPos.add(2 * offset15,2 * offset15));
    private final Point2D upgradeButtonPos = backButtonPos.subtract(0, 2 * backButtonDim.getY());
    private final Point2D showcasePos = new Point2D(menuPos.getX() + menuDim.getX() + offset15 + offset100 , menuPos.getY() + offset100);
    private final Point2D showcaseDim = new Point2D(Main.canvasSize.getX() - (menuDim.getX() + 2 * (offset15+ offset100)),(upgradeButtonPos.getY() - menuPos.getY()) - 2 * (offset15 + offset100) );
    private final Point2D upgradeTextPos = new Point2D(showcasePos.getX() + (showcaseDim.getX() * .5), menuPos.getY());
    private final Point2D upgradeTextDim = new Point2D(0, offset100);
    private final List<Invocation> invocations;
    private Invocation selectedInvocation;
    private final Button shardsButton, backButton, upgradeButton, upgradeText;
    private int shards, cost, upgradeCount;

    public InvocationManagementScene(double rP) {
        super(rP);
        this.invoMenu = new GraphicalInventory<>(6,3,menuPos,menuDim,10,70);
        this.upgradeShowcaseMenu = new GraphicalInventory<>(1,2,showcasePos,showcaseDim,offset100,70);
        this.upgradeShowcaseMenu.setRenderBackground(true);
        this.invocations = Invocation.getAll();

        this.upgradeCount = ContentEngine.TEXT.getPersistentGameplayValue("invocation_upgrade_count");
        this.cost = 5 + upgradeCount;
        this.shards = ContentEngine.TEXT.getPersistentGameplayValue("invocation_shards");
        this.shardsButton = new Button(shardsPos, shardsDim.getX(), shardsDim.getY(),
                new RText("Shards: " + shards, Point2D.ZERO,3,InGameScreen.soulColor, Font.font("Impact", 100 * Main.scale.getX())),
                false);
        this.shardsButton.setTextAlignments(0.5,0.3);
        this.upgradeText = new Button(upgradeTextPos, upgradeTextDim.getX(), upgradeTextDim.getY(),
                new RText("UPGRADE", Point2D.ZERO,3,InGameScreen.goldColor, Font.font("Impact", 80 * Main.scale.getX())),
                false);
        this.upgradeText.setTextAlignments(0.5,0.3);

        upgradeButton = new Button(ContentEngine.BUTTONS.getImage("BottomBeveledUpgradeFill"), upgradeButtonPos,backButtonDim,
                new RText(cost + " Shards", Point2D.ZERO,8,InGameScreen.goldColor, Font.font("Impact", 100 * Main.scale.getX())),
                null){
            @Override
            public void onClick(MouseEvent event){
                attemptUpgrade();
            }
        }.setTextAlignments(0.5,0.3);

        backButton = new Button(ContentEngine.BUTTONS.getImage("ArrowedButtonLeftFillBlack"),backButtonPos,backButtonDim,
                new RText("Back", Point2D.ZERO,8, Color.WHITE, Font.font("Impact", 100 * Main.scale.getX())),null){
            @Override
            public void onClick(MouseEvent event){
                Main.setState(UIController.getPrevious());
            }
        }.setShouldRenderBackground(true).setTextAlignments(0.5,0.3);

        reload();

        group.is(List.of(
                invoMenu,
                shardsButton,
                backButton,
                upgradeButton,
                upgradeShowcaseMenu,
                upgradeText
        ));
    }

    private void attemptUpgrade(){
        //Order of operations: Write to file. reloadInvocations. Reload gui
        if(shards >= cost) {
            if(selectedInvocation != null) {
                if (selectedInvocation.setLevel(selectedInvocation.getLevel() + 1)) {
                    ContentEngine.TEXT.updateInvocationLevels(invocations);
                    ContentEngine.TEXT.setPersistentGameplayValue("invocation_upgrade_count", upgradeCount + 1);
                    ContentEngine.TEXT.setPersistentGameplayValue("invocation_shards", shards - cost);
                    Invocation.reloadContent();
                    reload();
                }else{
                    new OnScreenWarning("Max Level Reached!", Main.canvasSize.multiply(.5),3).spawn();
                }
            }else{
                new OnScreenWarning("No Invocation Selected!", Main.canvasSize.multiply(.5),3).spawn();
            }
        }else{
            new OnScreenWarning("Not Enough Funds!", Main.canvasSize.multiply(.5),3).spawn();
        }
    }

    private void selectInvocation(Invocation selected){
        selectedInvocation = selected;
        upgradeShowcaseMenu.clear();
        SmallDisplayableDisplay<Invocation> sDD =
                new SmallDisplayableDisplay<>(Point2D.ZERO,new Point2D(400,400),null,true,selected);
        SmallDisplayableDisplay<Invocation> sDDLevelUp =
                new SmallDisplayableDisplay<>(Point2D.ZERO,new Point2D(400,400),null,true,selected.copy(selected.getLevel() + 1));

        upgradeShowcaseMenu.add(sDD);
        upgradeShowcaseMenu.add(sDDLevelUp);
    }

    private void reload(){
        upgradeCount = ContentEngine.TEXT.getPersistentGameplayValue("invocation_upgrade_count");
        cost = 5 + upgradeCount;
        shards = ContentEngine.TEXT.getPersistentGameplayValue("invocation_shards");
        upgradeButton.getText().setText(cost + " Shards");
        shardsButton.getText().setText(shards + " Shards");
        reloadInvocations();
        resetShowcaseToNewer();
    }

    private void reloadInvocations(){
        invoMenu.clear();
        List<SmallDisplayableDisplay<Invocation>> buttons = new ArrayList<>();
        for(Invocation invo : invocations){
            SmallDisplayableDisplay<Invocation> sDD = new SmallDisplayableDisplay<>(Point2D.ZERO,menuDim,null,true,invo){
                @Override
                public void onClick(MouseEvent event){
                    selectInvocation(this.getObj());
                }
            };
            sDD.getTitle().setTextColor2(Color.WHITE);
            buttons.add(sDD);
        }
        invoMenu.addAll(buttons);
    }

    private void resetShowcaseToNewer(){
        SmallDisplayableDisplay<Invocation> sDD = upgradeShowcaseMenu.get(0);
        if(sDD == null){return;}
        upgradeShowcaseMenu.clear();
        selectInvocation(sDD.getObj());
    }
}
