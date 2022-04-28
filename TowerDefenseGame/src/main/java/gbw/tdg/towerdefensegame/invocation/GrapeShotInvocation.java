package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.AugmentedBullet;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.backend.Point2G;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class GrapeShotInvocation extends BasicDMGInvocation{

    //Yes this bad boy works as expected with multishot.

    private int grapes;

    public GrapeShotInvocation(int level) {
        super(level);
        grapes = getLevel() + 3;
    }

    @Override
    public void attack(List<Enemy> targets){
        if(getOwner() == null){return;}

        Point2D position = getOwner().getOrigin();
        List<Bullet> bullets = new ArrayList<>();

        //First, calculate main bullet that always flies towards the main target
        for(Enemy e : targets) {

            Point2D vecToTarget = e.getPosition().subtract(position).normalize();
            bullets.add(super.applyAugs(new Bullet(position,vecToTarget,getOwner())));


            for (int i = 0; i < grapes; i++) {
                Point2D randVel = Point2G.getRandomlyScewedVector(vecToTarget,0.5);
                bullets.add(super.applyAugs(new Bullet(position, randVel, getOwner())).setDamagePercent(0.6));
            }

            //FIRE IN THE HOLE!
            for (Bullet b : bullets) {
                b.spawn();
            }
            bullets.clear();
        }
    }

    @Override
    public String getLongDesc(){
        return "Each shot fires an additional " + grapes + " grapes dealing 60% damage. The main bullet will always be fired in the direction of the target, but the grapes will be fired within a ~45 degree angle. \n Do you really need precision if the air they breath is lead?";
    }

    @Override
    public String getDesc(){
        return "Fires a grapeshot of " + grapes + " pellets along with the original bullet.";
    }

    @Override
    public Invocation copy(){
        return new GrapeShotInvocation(getLevel());
    }

}
