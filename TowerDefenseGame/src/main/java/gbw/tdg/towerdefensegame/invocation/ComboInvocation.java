package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.BounceBackBullet;
import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.ProgressBar;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.augments.BounceReciever;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class ComboInvocation extends BasicDMGInvocation implements BounceReciever<Enemy, Bullet>, Tickable {

    private int maxTimeLeft = 3_000, stacks, boost = 500, gracePeriod = 100;
    private long timeLeft, lastCall;
    private final Button stackCountText;
    private final FancyProgressBar timeLeftBar;

    public ComboInvocation(int level) {
        super(level);
        RText textUnit = new RText("x0",Point2D.ZERO,6, Color.RED, Font.font("Impact", 50 * Main.scale.getX()));
        this.stackCountText = new Button(Point2D.ZERO,0,50,textUnit,null,false);
        this.timeLeftBar = new FancyProgressBar(200 * Main.scale.getX(),50 * Main.scale.getY(),Point2D.ZERO,Color.CYAN,Color.BLACK);
    }

    @Override
    public void attack(List<Enemy> targets){
        if(getOwner() == null){return;}

        Point2D position = getOwner().getOrigin();

        for(Enemy target : targets) {
            applyAugs(new BounceBackBullet(position, target, getOwner(),this).setDamagePercent(1 + stacks / 10D)).spawn();
        }
    }

    @Override
    public void tick() {
        long diff = System.currentTimeMillis() - lastCall;
        lastCall = System.currentTimeMillis();
        timeLeft -= diff;
        if(timeLeft <= 0){
            stacks = 0;
            timeLeft = 0;
        }
        stackCountText.getText().setText("x"+stacks);
        timeLeftBar.setVal((double) timeLeft / maxTimeLeft);
    }

    private void extendTimeout() {
        if(timeLeft + boost > maxTimeLeft){
            timeLeft = maxTimeLeft;
        }else {
            timeLeft += boost;
        }

        if(stacks == 0){
            timeLeft = maxTimeLeft;
        }
    }

    @Override
    public void bounce(Enemy enemy, Bullet bullet) {
        extendTimeout();
        stacks += getLevel();
    }

    private void attachStackCountText(Tower t){
        stackCountText.setRoot(null);
        stackCountText.setPosition(t.getOrigin().add(0,t.getDimensions().getY() * .5));
        stackCountText.setTextAlignments(0.5,0.3);
        stackCountText.setRenderingPriority(t.getRenderingPriority() + 1);
        stackCountText.spawn();

        timeLeftBar.setPosition(stackCountText.getPosition().subtract(timeLeftBar.getDimensions().getX() * .5,-stackCountText.getText().getSize() * .5));
        timeLeftBar.setRenderingPriority(t.getRenderingPriority() +.1);
        timeLeftBar.spawn();
    }

    @Override
    public boolean applyToTower(Tower t){
        spawn();
        boost = (int) Math.min(boost,t.getAttackDelayMS()) + gracePeriod;
        attachStackCountText(t);
        return super.applyToTower(t);
    }
    @Override
    public Invocation copy(int i){
        return new ComboInvocation(Math.min(i, getMaxLevel()));
    }

    public String getName(){
        return "Combo " + TextFormatter.toRomanNumerals(getLevel());
    }

    public String getDesc(){
        return "Hitting enemies consecutively adds " + getLevel() + " stacks. Each stack adds 10% bonus damage to all bullets.";
    }
    public String getLongDesc(){
        return "Hitting enemies consecutively adds " + getLevel() + " stacks. Each stack adds 10% bonus damage to all bullets, however the amount of stacks are" +
                " reset if the no bullets have hit an enemy in a too long time span";
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }
}
