package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DummyBullet implements Tickable,Renderable{

    private static final double renderingPriority = 65D;
    private Point2D position, velocity;
    private double speed = 20, lifeTime = 2 * 1_000_000_000, spawnTime, damage, size = 10;
    private IEnemy target;

    public DummyBullet(Point2D position, IEnemy target, double damage){
        this.position = position;
        this.velocity = new Point2D(0,0);
        this.target = target;
        this.damage = damage;
        this.speed = target.getMvspeed() * 2;
        spawnTime = System.nanoTime();
    }

    public void tick(){
        velocity = target.getPosition().subtract(position).normalize();
        position = position.add(velocity.multiply(speed));

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

    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }

    private void checkForCollision(){
        double dist = target.getPosition().subtract(position).magnitude();

        if(dist < target.getSize()){
            target.changeHp(-damage);
            destroy();
        }
    }

}
