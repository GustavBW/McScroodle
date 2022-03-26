package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GravityHandler implements Tickable{

    public static BigDecimal gConst = BigDecimal.valueOf(6.67 * Math.pow(10, -11));
    public static double GRAVITY_MULTIPLIER = 1;

    @Override
    public void tick() {
        for(MassEffected mA : MassEffected.active){

            //Evaluate against static mass objects
            for(StaticMass sM : StaticMass.active){
                applyGravity(mA, mA.getPosition(), mA.getMass(), sM.getPosition(), sM.getMass());
            }

            //Evaluate against non-static mass effected objects
            for(MassEffected mA2 : MassEffected.active){
                if(mA != mA2){
                    applyGravity(mA, mA.getPosition(), mA.getMass(), mA2.getPosition(), mA2.getMass());
                }
            }
        }

        MassEffected.active.removeAll(MassEffected.expended);
        MassEffected.active.addAll(MassEffected.newborn);
        MassEffected.expended.clear();
        MassEffected.newborn.clear();

        StaticMass.active.removeAll(StaticMass.expended);
        StaticMass.active.addAll(StaticMass.newborn);
        StaticMass.newborn.clear();
        StaticMass.expended.clear();
    }

    private void applyGravity(MassEffected obj1, Point2D obj1Pos, double obj1Mass, Point2D obj2Pos, double obj2Mass ) {
        double distanceSQ = Math.pow(obj2Pos.distance(obj1Pos),2);
        Point2D direction = obj2Pos.subtract(obj1Pos);
        BigDecimal force = (     //Force calc = (gConst * m1 * m2) / dist * dist
                gConst.multiply(BigDecimal.valueOf(obj2Mass * obj1Mass)))
                .divide(BigDecimal.valueOf(distanceSQ), RoundingMode.UP)
                .multiply(BigDecimal.valueOf(GRAVITY_MULTIPLIER)
                );
        obj1.evaluateGravity(force, direction.normalize());
    }

    @Override
    public Point2D getPosition() {
        return null;
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }
}
