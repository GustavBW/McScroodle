package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.UI.buttons.OpenDisplayableDisplayButton;
import gbw.tdg.towerdefensegame.UI.scenes.InGameScreen;
import gbw.tdg.towerdefensegame.backend.ContentEngine;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class UpgradingDisplayableDisplay<T extends Displayable> extends SmallDisplayableDisplay<T> {

    private final Button button;
    private final RText costText;

    public UpgradingDisplayableDisplay(Point2D position, Point2D dim, Clickable root, boolean shouldRenderBackground, T t) {
        super(position, dim, root, shouldRenderBackground, t);

        costText = new RText("X.XS",Point2D.ZERO,6, InGameScreen.soulColor, Font.font("Impact",50 * Main.scale.getX()));

        button = new Button(
                ContentEngine.BUTTONS.getImage("UpButtonBase"),getButtonOffset(),getButtonDim(),costText,root){
            @Override
            public void onClick(MouseEvent event){
                click(event);
            }
        }
                .setTextAlignments(.5,.3)
                .setShouldRenderBackground(false);
        button.setRenderingPriority(super.getRenderingPriority() + .1);

        setPosition(super.getPosition());
    }

    @Override
    public RText getText(){
        return costText;
    }

    @Override
    public void update(){
        super.update();
        button.alignText();
    }

    public void click(MouseEvent event){}

    private Point2D getButtonOffset(){
        return new Point2D(
                (getDimensions().getX() - getButtonDim().getX()) * .5,
                getDimensions().getY() - (getButtonDim().getY() * .5)
        );
    }

    private Point2D getButtonDim(){
        return new Point2D(
                getDimensions().getX() * 2/3D,
                getDimensions().getY() * .1
        );
    }

    @Override
    public void setPosition(Point2D p){
        super.setPosition(p);
        if(button == null){return;}
        button.setPosition(p.add(getButtonOffset()));
    }
    @Override
    public void setDimensions(Point2D p){
        super.setDimensions(p);
        if(button == null){return;}
        button.setDimensions(getButtonDim());
    }

    @Override
    public void spawn(){
        super.spawn();
        button.spawn();
    }

    @Override
    public void destroy(){
        super.destroy();
        button.destroy();
    }

    @Override
    public boolean isInBounds(Point2D p){
        return false;
    }
}
