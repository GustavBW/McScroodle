package gbw.gravityslingshot.gravityslingshot;

public class GravityHandler implements Tickable{

    @Override
    public void tick() {
        for(MassEffected mA : MassEffected.active){



        }


        MassEffected.active.removeAll(MassEffected.expended);
        MassEffected.active.addAll(MassEffected.newborn);
        MassEffected.expended.clear();
        MassEffected.newborn.clear();
    }

}
