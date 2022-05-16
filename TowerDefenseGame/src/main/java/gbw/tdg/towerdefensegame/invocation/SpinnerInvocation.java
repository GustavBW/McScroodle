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

    }

    private List<Point2D> getVelocities(int a){
        List<Point2D> list = new ArrayList<>();
        double factor = (2D / getLevel());
        for(int i = 0; i <= a; i++){
            double yVal = i * factor;
            double xVal = 2 - yVal;
            yVal -= 1;
            xVal -= 1;

            list.add(new Point2D(xVal,yVal).normalize());
        }
        return list;
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
