package gbw.tdg.towerdefensegame.enemies;

import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.augments.ExplosiveAugment;

import java.util.LinkedList;
import java.util.List;

public interface IEnemy extends Renderable {

    List<IEnemy> active = new LinkedList<>();
    List<IEnemy> expended = new LinkedList<>();
    List<IEnemy> newborn = new LinkedList<>();

    double getProgress();
    double getHp();
    double getMvspeed();
    double getSize();
    void onHitByBullet(Bullet b, boolean onHit);

    void applyAugmentEffect(Augment a);

    void addIgnoredAug(Augment a);
    void removeIgnoredAug(Augment a);
}
