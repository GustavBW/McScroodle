package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DummyBullet implements Tickable{

    private Point2D position, velocity;
    private double speed = 20, lifeTime = 2 * 1_000_000_000, spawnTime, damage;
    private Enemy target;

    public DummyBullet(Point2D position, Point2D velocity, Enemy target, double damage){
        this.position = position;
        this.velocity = velocity;
        this.target = target;
        this.damage = damage;
        spawnTime = System.nanoTime();
        Main.addTickable.add(this);
    }

    public void tick(){
        position = position.add(velocity.multiply(speed));

        velocity = target.getPosition().subtract(position).normalize();

        if(System.nanoTime() > lifeTime + spawnTime){
            destroy();
        }

        checkForCollision();
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(position.getX() -3,position.getY() -3,6,6,6,6);
    }

    private void destroy(){
        Main.removeProjectile.add(this);
        Main.removeTickable.add(this);
    }

    private void checkForCollision(){
        double dist = target.getPosition().subtract(position).magnitude();

        if(dist < target.getSize()){
            target.changeHp(-damage);
            destroy();
        }
    }

}
