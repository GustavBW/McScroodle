package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.vfx.CircleVFX;
import gbw.tdg.towerdefensegame.UI.vfx.VFX;
import gbw.tdg.towerdefensegame.augments.SlowEffect;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Set;

public class SlowFieldInvocation extends BasicSPDInvocation {

    private double fieldRange;
    private double slowPercent;
    private final CircleVFX circleVFX;

    public SlowFieldInvocation(int level) {
        super(level);
        fieldRange = getLevel() * 200 * Main.scale.getX();
        slowPercent = 1 / (getLevel() + 0.5);
        super.setUseTowerRange(false);
        super.setSearchRange(fieldRange);
        circleVFX = new CircleVFX(300, VFX.DEFAULT_PRIO, Point2D.ZERO,fieldRange)
                .setColor(new Color(94 / 255D,1,247/255D,0.4));
    }

    @Override
    public Invocation copy() {
        return new SlowFieldInvocation(getLevel());
    }

    @Override
    public void attack(List<Enemy> possibleTargets) {}

    @Override
    public void evaluate() {
        circleVFX.setPosition(getOwner().getOrigin());
        circleVFX.setRadius(getFieldRange(getOwner()));
        Set<Enemy> enemiesInRange = super.findEnemiesInRange();


        for(Enemy e : enemiesInRange){
            e.addLifetimeEffect(new SlowEffect(500,slowPercent,this));
        }
        circleVFX.spawn();
    }

    @Override
    public List<Enemy> preattack() {
        return null;
    }

    @Override
    public boolean applyToTower(Tower t) {
        t.setRNGInvocation(this);
        fieldRange = getFieldRange(t);
        circleVFX.setRenderingPriority(t.getRenderingPriority() - .1);
        return true;
    }

    private double getFieldRange(Tower t) {
        return t.getRange() * 0.4;
    }

    @Override
    public String getDesc(){
        return "Slows nearby enemies for " + (int) (100 - (slowPercent * 100)) + "%";
    }

    @Override
    public String getLongDesc(){
        return "Enemies around the Tower are slowed by " + (int)  (100 - (slowPercent * 100)) + "%. The effects' range scales with the Tower's range. \n\"An icy aura surrounds the Tower. Freezing any unfortunate bypassers to the bone.\"";
    }
}
