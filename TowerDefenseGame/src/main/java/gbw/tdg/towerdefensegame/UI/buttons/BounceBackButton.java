package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.UI.IClickableOwner;
import gbw.tdg.towerdefensegame.UI.RText;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class BounceBackButton extends Button{

    private IClickableOwner owner;

    public BounceBackButton(Point2D position, double sizeX, double sizeY, RText textUnit, IClickableOwner owner) {
        super(position, sizeX, sizeY, textUnit, true);
        this.owner = owner;
    }

    public void setOwner(IClickableOwner obj){
        this.owner = obj;
    }

    @Override
    public void onPress(MouseEvent event){
        if(owner == null){return;}
        owner.onChildPress(this,event);
    }
    @Override
    public void onClick(MouseEvent event){
        if(owner == null){return;}
        owner.onChildClick(this,event);
    }
    @Override
    public void onRelease(MouseEvent event){
        if(owner == null){return;}
        owner.onChildRelease(this,event);
    }

}
