package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.AugmentIcon;

public abstract class Augment {

    protected Tower tower;
    protected final double value;
    protected final AugmentIcon icon;

    public Augment(double value, AugmentIcon icon){
        this.value = value;
        this.icon = icon;

    }

    public abstract void applyTo(Bullet bullet);
    public void setTower(Tower newTower){
        this.tower = newTower;
    }
}
