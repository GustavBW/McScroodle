package gbw.tdg.towerdefensegame.tower;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import javafx.geometry.Point2D;

import java.util.HashSet;
import java.util.Set;

public abstract class ITower extends Button implements Clickable, Renderable, Tickable {

    public static Set<ITower> active = new HashSet<>();
    public static Set<ITower> expended = new HashSet<>();
    public static Set<ITower> newborn = new HashSet<>();

    public static Tower getOnPos(Point2D p){
        for(ITower t : active){
            if(t.isInBounds(p)){
                return (Tower) t;
            }
        }
        return null;
    }

    public ITower(Point2D position, double sizeX, double sizeY) {
        super(position, sizeX, sizeY, RText.EMPTY, false);
    }

    public abstract double getDamage();
}
