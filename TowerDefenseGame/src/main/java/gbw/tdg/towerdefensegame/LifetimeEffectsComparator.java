package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.augments.LifetimeEffect;

import java.util.Comparator;

public class LifetimeEffectsComparator implements Comparator<LifetimeEffect> {
    @Override
    public int compare(LifetimeEffect o1, LifetimeEffect o2) {

        if(o1 == o2 || o1.getOwner().equals(o2.getOwner())){
            return 0;
        }

        return -1;
    }
}
