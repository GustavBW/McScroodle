package gbw.roguelike.collisions;

import gbw.roguelike.Vector2D;
import gbw.roguelike.interfaces.Collidable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ColliderManager {



    public Vector2D evaluateCollisions(Vector2D pos, Vector2D vec){
        Vector2D output = vec;
        Set<Collidable> foundCollisions = new HashSet<>();

        //Firstly, find all colliders the movement would intersect with
        for(Collidable c : Collidable.collidables){
            if(c.intersects(pos,vec)){
                foundCollisions.add(c);
            }
        }
        if(foundCollisions.size() < 1){
            return output;
        }

        //Then sort by distance to the position used
        ArrayList<Collidable> sortedCollisions = new ArrayList<>(foundCollisions);
        sortedCollisions.sort(Comparator.comparingDouble(o -> o.getPosition().distanceToSQ(pos)));
        //TODO check if this actually works as expected

        //If the closest is a dynamic collider, the impact vector vec should be reduced proportionally to the mass of the first collider
        //But then be applied to all other intersected colliders that is also dynamic until one isn't.
        //Since dynamic colliders also needs to be checked for collisions when bumped into, some recursive method might be needed.

        for(Collidable c : sortedCollisions){



            if(!c.isDynamic()){

                break;
            }
        }


        return output;
    }

}
