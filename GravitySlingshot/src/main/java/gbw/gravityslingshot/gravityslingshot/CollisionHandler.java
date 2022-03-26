package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;

public class CollisionHandler implements Tickable {


    @Override
    public void tick() {
        boolean collisionOrderFound = false;

        for(Collidable c : Collidable.active){
            for(Collidable c2 : Collidable.active){
                if(c != c2){
                    if(c.isInBounds(c2.getPosition())){
                        c.onCollisionWith(c2);
                        c2.onCollisionWith(c);
                        collisionOrderFound = true;
                    }
                    if(c2.isInBounds(c.getPosition()) && !collisionOrderFound){
                        c2.onCollisionWith(c);
                        c.onCollisionWith(c2);
                    }
                }
            }
        }

        Collidable.active.addAll(Collidable.newborn);
        Collidable.active.removeAll(Collidable.expended);
        Collidable.expended.clear();
        Collidable.newborn.clear();
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
