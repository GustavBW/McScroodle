package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Collidable;

import java.util.HashSet;
import java.util.Set;

public interface IProjectile extends Collidable {

    Set<IProjectile> active = new HashSet<>();
    Set<IProjectile> expended = new HashSet<>();
    Set<IProjectile> newborn = new HashSet<>();

}
