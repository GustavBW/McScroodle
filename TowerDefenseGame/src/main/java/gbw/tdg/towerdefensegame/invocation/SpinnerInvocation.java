package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class SpinnerInvocation extends BasicDMGInvocation{

    public SpinnerInvocation(int level) {
        super(level);
    }

    @Override
    public void attack(List<Enemy> targets){
        if(getOwner() == null){return;}

        Point2D oPos = getOwner().getPosition();
        List<Bullet> wave = new ArrayList<>();
        double factor = (2D / getLevel()) - 1;

        for(int i = 0; i <= getLevel(); i++){
            double xVal = i * factor;
            double yVal = (2D * i) / (i + getLevel());
            Point2D vel = new Point2D(xVal,yVal).normalize();

            wave.add(new Bullet());
        }

    }

    @Override
    public Invocation copy(int i){
        return new SpinnerInvocation(Math.min(i,getMaxLevel()));
    }
    @Override
    public String getDesc(){
        return "";
    }

    @Override
    public String getLongDesc(){
        return getDesc();
    }
    @Override
    public String getName(){
        return "Spinner";
    }

}
