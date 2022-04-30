package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.Displayable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.SmallDisplayableDisplay;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class OpenDisplayableDisplayButton<T extends Displayable> extends Button{

    private final SmallDisplayableDisplay<T> invoDisplay;
    private boolean toggle = true;

    public OpenDisplayableDisplayButton(Point2D position, Point2D dim, Clickable root, T t) {
        super(t.getImage(),position, dim, RText.EMPTY, root);
        Point2D posOfDisplay = new Point2D(
                Main.canvasSize.getX() * 0.11,
                Main.canvasSize.getY() * 0.3
        );
        Point2D dimOfDisplay = new Point2D(
                Main.canvasSize.getX() * 0.13,
                Main.canvasSize.getY() * 0.3
        );
        this.invoDisplay = new SmallDisplayableDisplay<>(posOfDisplay,dimOfDisplay,root,false,t);
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