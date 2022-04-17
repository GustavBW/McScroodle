package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet implements Tickable,Renderable{

    private static final double renderingPriority = 65D;
    private Point2D position, velocity;
    private double lifeTime = 10 * 1_000, spawnTime, sizeX = 10,sizeY = 10;
    protected double speed = 40, damage;
    protected IEnemy target;
    protected ITower owner;

    public Bullet(Point2D position, IEnemy target, double damage, ITower owner){
        this.position = position;
        this.velocity = new Point2D(0,0);
        this.target = target;
        this.damage = damage;
        //this.speed = target.getMvspeed() * 2;
        this.owner = owner;
        spawnTime = System.currentTimeMillis();
    }

    public void tick(){
        velocity = target.getPosition().subtract(position).normalize();
        position = position.add(velocity.multiply(speed));

        if(System.currentTimeMillis() > lifeTime + spawnTime){
            destroy();
        }

        checkForCollision();
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(position.getX() -sizeX / 2,position.getY() -sizeY / 2,sizeX,sizeY,sizeX,sizeY);
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
    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }
    @Override
    public void setDimensions(Point2D dim) {
        this.sizeX = dim.getX();
        this.sizeY = dim.getY();
    }
    private void checkForCollision(){
        double dist = target.getPosition().subtract(position).magnitude();

        if(dist < target.getSize()){
            onCollision();
        }
    }
    public ITower getOwner(){return owner;}
    public double getDamage(){return damage;}
    protected void onCollision(){
        target.onHitByBullet(this);
        destroy();
    }

}
