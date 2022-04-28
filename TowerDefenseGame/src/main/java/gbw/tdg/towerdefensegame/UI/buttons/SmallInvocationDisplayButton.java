package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.SmallInvocationDisplay;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class SmallInvocationDisplayButton extends Button{

    private final SmallInvocationDisplay invoDisplay;
    private boolean toggle = true;

    public SmallInvocationDisplayButton(Point2D position, Point2D dim, Clickable root, Invocation invo) {
        super(invo.getImage(),position, dim, RText.EMPTY, root);
        Point2D posOfDisplay = new Point2D(
                Main.canvasSize.getX() * 0.11,
                Main.canvasSize.getY() * 0.3
        );
        Point2D dimOfDisplay = new Point2D(
                Main.canvasSize.getX() * 0.13,
                Main.canvasSize.getY() * 0.3
        );
        this.invoDisplay = new SmallInvocationDisplay(posOfDisplay,dimOfDisplay,root,false,invo);
    }

    @Override
    public void onClick(MouseEvent event){
        if(!toggle){
            invoDisplay.destroy();
        }else{
            invoDisplay.spawn();
        }
        toggle = !toggle;
    }

    @Override
    public void spawn(){
        super.spawn();
    }

    @Override
    public void destroy(){
        super.destroy();
        invoDisplay.destroy();
        toggle = false;
    }


}
