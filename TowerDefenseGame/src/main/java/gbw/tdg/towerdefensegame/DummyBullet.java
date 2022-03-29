package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DummyBullet implements Tickable,Renderable{

    private Point2D position, velocity;
    private double speed = 20, lifeTime = 2 * 1_000_000_000, spawnTime, damage, size = 10;
    private Enemy target;

    public DummyBullet(Point2D position, Point2D velocity, Enemy target, double damage){
        this.position = position;
        this.velocity = velocity;
        this.target = target;
        this.damage = damage;
        this.speed = target.getMvspeed() * 2;
        spawnTime = System.nanoTime();
    }

    public void tick(){
        position = position.add(velocity.multiply(speed));

        velocity = target.getPosition().subtract(position).normalize();

        if(System.nanoTime() > lifeTime + spawnTime){
            destroy();
        }

        checkForCollision();
    }
    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(position.getX() -size / 2,position.getY() -size / 2,size,size,size,size);
    }

    @Override
    public void destroy(){
        Tickable.expended.add(this);
        Renderable.expended.add(this);
    }
    @Override
    public void spawn(){
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
    }
    @Override
    public Point2D getPosition(){
        return position;
    }

    private void checkForCollision(){
        double dist = target.getPosition().subtract(position).magnitude();

        if(dist < target.getSize()){
            target.changeHp(-damage);
            destroy();
        }
    }

}
