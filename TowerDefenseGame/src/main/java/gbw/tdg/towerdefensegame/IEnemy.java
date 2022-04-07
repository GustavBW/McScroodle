package gbw.tdg.towerdefensegame;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface IEnemy extends Renderable {

    List<IEnemy> active = new LinkedList<>();
    List<IEnemy> expended = new LinkedList<>();
    List<IEnemy> newborn = new LinkedList<>();

    double getProgress();
    int getHp();
    double getMvspeed();
    double getSize();
    void onHitByBullet(Bullet b);
}
