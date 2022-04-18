package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.tower.Tower;

public abstract class Invocation {

    protected Tower tower;

    public Invocation(){

    }

    public abstract void evalutate();

    public void setTower(Tower newTower) {
        this.tower = newTower;
    }
}
