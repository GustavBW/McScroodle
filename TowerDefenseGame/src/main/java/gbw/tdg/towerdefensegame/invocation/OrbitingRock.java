package gbw.tdg.towerdefensegame.invocation;

import gbw.tdg.towerdefensegame.Bullet;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import gbw.tdg.towerdefensegame.tower.ITower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class OrbitingRock extends Bullet {

    private Orbit orbit;

    public OrbitingRock(Point2D center, double radius, ITower owner, double orbitingSpeed, double orbitOffset) {
        super(center, Point2D.ZERO, owner);
        this.orbit = new Orbit(center,radius,orbitingSpeed,orbitOffset);
    }

    @Override
    public void tick(){
        super.setPosition(orbit.getNext());
        applyDamageTo(checkCollisions());
    }

    public Orbit getOrbit(){
        return orbit;
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillRect(getPosition().getX(), getPosition().getY(),10,10);
    }

    private List<Enemy> checkCollisions(){
        List<Enemy> collisionsFound = new ArrayList<>();

        for(IEnemy e : IEnemy.active){
            if(e.getPosition().distance(super.getPosition()) < e.getSize()){
                collisionsFound.add((Enemy) e);
            }
        }
        return collisionsFound;
    }

    private void applyDamageTo(List<Enemy> list){
        for(Enemy e : list){
            e.applyDamage(owner.getDamage() * 10);
        }
    }

    public double getDamage(){
        return owner == null ? 10 : owner.getDamage() * 10;
    }

}
