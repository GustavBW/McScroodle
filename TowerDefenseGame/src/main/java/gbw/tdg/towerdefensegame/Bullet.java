package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.enemies.IEnemy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bullet implements Tickable,Renderable{

    private double renderingPriority = 65D;
    private Point2D position, velocity;
    private double lifeTime = 10 * 1_000, spawnTime, sizeX = 10,sizeY = 10;
    protected double speed = 40, damage;
    protected IEnemy target;
    protected List<IEnemy> hasAlreadyHit = new ArrayList<>();
    private List<Augment> onHitAugments = new ArrayList<>();
    private List<Augment> inFlightAugments = new ArrayList<>();
    private List<Augment> onSpawnAugments = new ArrayList<>();
    protected ITower owner;
    private boolean targeted;
    private int piercingLevel = 1;

    public Bullet(Point2D position, IEnemy target, ITower owner){
        this.position = position;
        this.velocity = new Point2D(0,0);
        this.target = target;
        //this.speed = target.getMvspeed() * 2;
        this.owner = owner;
        spawnTime = System.currentTimeMillis();
        targeted = target != null;
    }

    public Bullet(Point2D position, Point2D velocity, ITower owner){
        this(position, (IEnemy) null,owner);
        this.velocity = velocity;
    }

    public void tick(){
        if(targeted) {
            velocity = target.getPosition().subtract(position).normalize();
        }

        position = position.add(velocity.multiply(speed));

        if(System.currentTimeMillis() > lifeTime + spawnTime){
            destroy();
        }

        if(targeted) {
            checkForTargetedCollision();
        }else{
            checkForCollision();
        }
    }

    private void checkForCollision() {
        List<IEnemy> collisionsFound = new ArrayList<>();

        for(IEnemy e : IEnemy.active){
            if(e.getPosition().distance(position) < e.getSize()){
                collisionsFound.add(e);
            }
        }

        if(!collisionsFound.isEmpty()) {

            collisionsFound.remove(hasAlreadyHit);
            collisionsFound.sort(Comparator.comparingDouble(o -> o.getPosition().distance(position)));
            for(IEnemy e : collisionsFound){
                onCollision(e);
            }

        }
    }

    private void checkForTargetedCollision(){
        double dist = target.getPosition().subtract(position).magnitude();

        if (dist < target.getSize()) {
            onCollision(target);
        }
    }
    public void setPiercingLevel(int lvl){this.piercingLevel = lvl;}
    public ITower getOwner(){return owner;}
    public double getDamage(){return owner.getDamage();}
    protected void onCollision(IEnemy enemyHit){
        enemyHit.onHitByBullet(this);
        hasAlreadyHit.add(enemyHit);
        piercingLevel--;
        for(Augment a : onHitAugments){
            a.applyToEnemy((Enemy) enemyHit, this);
        }

        if(piercingLevel == 0) {
            destroy();
        }
        targeted = false;
    }
    public void addOnHitAug(Augment aug){
        onHitAugments.add(aug);
    }
    public void addInFlightAug(Augment aug){
        inFlightAugments.add(aug);
    }
    public void addOnSpawnAug(Augment aug){
        onSpawnAugments.add(aug);
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
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
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

}
