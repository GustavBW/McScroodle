package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.W;

public class Player extends GameObject implements Renderable, Tickable, Collidable {

    private final double mvspeed = 1, size = 30;
    private final double maxSpeed = 10;
    private final double drag = 0.95;
    private final double maxHealth = 100;
    private double healthPoints = maxHealth;

    private Point2D position;
    private Point2D velocity;
    private int id;
    private List<KeyEvent> currentInputs;


    public Player(Point2D pos, int id){
        position = pos;
        this.id = id;
        velocity = new Point2D(0,0);
        currentInputs = new ArrayList<>();
    }


    @Override
    public void tick() {

        for(KeyEvent event : currentInputs){
            switch (event.getCode()){
                case W -> {velocity = velocity.add(0,-1 * mvspeed); velocity = velocity.multiply(10 / velocity.magnitude());}
                case S -> {velocity = velocity.add(0,1 * mvspeed);  velocity = velocity.multiply(10 / velocity.magnitude());}
                case A -> {velocity = velocity.add(-1 * mvspeed,0); velocity = velocity.multiply(10 / velocity.magnitude());}
                case D -> {velocity = velocity.add(1 * mvspeed,0);  velocity = velocity.multiply(10 / velocity.magnitude());}
            }
        }

        if((position.getX() + velocity.getX() < WorldSpace.worldDimensions.getX() && position.getX() + velocity.getX() > 0) && (position.getY() + velocity.getY() < WorldSpace.worldDimensions.getY() && position.getY() + velocity.getY() > 0)) {
            position = position.add(velocity);
        }
        velocity = velocity.multiply(drag);
    }

    @Override
    public void render(GraphicsContext gc, Point2D worldSpaceOffset) {
        gc.setFill(Color.BLUE);
        gc.fillRect(worldSpaceOffset.getX() + position.getX(),worldSpaceOffset.getY() + position.getY(),size,size);

    }

    public void onTerrainCollision(){
        position = position.add(velocity.multiply(-1));
    }

    public DummyProjectile abilityShoot(Point2D mousePos){

        Point2D origin = new Point2D(position.getX() + size/2, position.getY() + size/2);

        Point2D relativeMousePosition = mousePos.subtract(WorldSpace.currentWorldSpaceOffset);
        Point2D velocityVector = relativeMousePosition.subtract(origin);
        DummyProjectile dp = new DummyProjectile(origin.add(velocityVector.normalize().multiply(10)), velocityVector ,this);

        TickHandler.addTickable(dp);
        WorldSpace.addRenderable(dp, LayerType.Middleground1);
        CollisionHandler.addCollidable(dp);

        induceRecoil(velocityVector, 3);
        return dp;
    }

    private void induceRecoil(Point2D vector, double strength){
        vector = vector.normalize();
        vector = vector.multiply(-1 * strength);
        velocity = velocity.add(vector);
    }

    @Override
    public boolean isInBounds(Point2D posOCol) {
        return (posOCol.getX() < position.getX() + size && posOCol.getX() > position.getX()) && (posOCol.getY() < position.getY() + size && posOCol.getY() > position.getY());
    }

    public void onCollision(Collidable c){
        Point2D vectorToC = position.subtract(c.getPosition());
        vectorToC = vectorToC.normalize();
        vectorToC = vectorToC.multiply(c.getKnockbackForce());
        velocity = velocity.add(vectorToC);
    }

    @Override
    public double getKnockbackForce() {
        return 0;
    }

    public void changeHealth(double amount){
        healthPoints += amount;
        System.out.println("HP changed to " + healthPoints);
    }
    public void destroy(){
        TickHandler.removeTickable(this);
        WorldSpace.removeRenderable(this, LayerType.Middleground0);
    }
    public Point2D getPosition(){
        Point2D origin = new Point2D(position.getX() + size / 2, position.getY() + size / 2);
        return origin;
    }
    public void addInput(KeyEvent event){

        boolean contains = false;
        for(KeyEvent k : currentInputs){
            if (k.getCode() == event.getCode()) {
                contains = true;
                break;
            }
        }

        if(!contains) {
            currentInputs.add(event);
        }
    }
    public void removeInput(KeyEvent event){
        for(int i = 0; i < currentInputs.size(); i++){
            if(currentInputs.get(i).getCode() == event.getCode()){
                currentInputs.remove(i);
            }
        }
    }


}
