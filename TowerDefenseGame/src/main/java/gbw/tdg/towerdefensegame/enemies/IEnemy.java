package gbw.tdg.towerdefensegame.enemies;

import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.augments.ExplosiveAugment;
import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.List;

public abstract class IEnemy extends Button implements Renderable {

    public static List<IEnemy> active = new LinkedList<>();
    public static List<IEnemy> expended = new LinkedList<>();
    public static List<IEnemy> newborn = new LinkedList<>();

    public IEnemy(Point2D position, double sizeX, double sizeY) {
        super(position, sizeX, sizeY, RText.EMPTY, false);
    }

    public double getProgress(){return 0;}
    public double getHp(){return 0;}
    public double getMvspeed(){return 0;}
    public double getSize(){return 0;}
    public void onHitByBullet(Bullet b, boolean onHit){}
}
