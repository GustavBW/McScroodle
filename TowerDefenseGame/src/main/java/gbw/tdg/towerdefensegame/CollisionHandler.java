package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Collidable;

public class CollisionHandler implements Tickable{


    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

    @Override
    public void tick() {
        for(Collidable c1 : Collidable.active){

            for(Collidable c2 : Collidable.active){
                if(c1 != c2){

                    if(c1.isInBounds(c2) || c2.isInBounds(c1)){
                        c1.onCollision(c2);
                        c2.onCollision(c1);
                    }

                }
            }
        }

        cleanUp();
    }

    private void cleanUp(){
        Collidable.active.addAll(Collidable.newborn);
        Collidable.active.removeAll(Collidable.expended);
        Collidable.newborn.clear();
        Collidable.expended.clear();
    }
}
