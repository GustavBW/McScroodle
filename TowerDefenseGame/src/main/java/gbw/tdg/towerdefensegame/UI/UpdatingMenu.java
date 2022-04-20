package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.Updating;
import javafx.geometry.Point2D;

public class UpdatingMenu<T extends Renderable> extends Menu<T> implements Tickable, Updating {

    public UpdatingMenu(Point2D pos, Point2D dim, double rendPrio, int coloumns, int rows) {
        super(pos, dim, rendPrio, coloumns, rows);
    }

    @Override
    public void tick() {
        onUpdate();
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

    @Override
    public void onUpdate() {

    }
}
