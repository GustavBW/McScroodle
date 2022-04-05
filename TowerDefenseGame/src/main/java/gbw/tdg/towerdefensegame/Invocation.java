package gbw.tdg.towerdefensegame;

public abstract class Invocation {

    protected Tower tower;

    public Invocation(){

    }

    public abstract void evalutate();

    public void setTower(Tower newTower) {
        this.tower = newTower;
    }
}
