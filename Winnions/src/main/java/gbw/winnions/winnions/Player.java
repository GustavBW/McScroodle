package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.W;

public class Player implements Renderable, Tickable {

    private final double mvspeed = 1, maxSpeed = 10, drag = 0.95, maxHealth = 100, healthPoints = maxHealth;

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


        position = position.add(velocity);
        velocity = velocity.multiply(drag);
    }

    @Override
    public void render(GraphicsContext gc, Point2D worldSpaceOffset) {

        gc.setFill(Color.BLUE);
        gc.fillRect(worldSpaceOffset.getX() + position.getX(),worldSpaceOffset.getY() + position.getY(),30,30);

    }

    public DummyProjectile abilityShoot(Point2D mousePos){

        DummyProjectile dp = new DummyProjectile(position, mousePos.subtract(position),this);
        TickHandler.addTickable(dp);
        WorldSpace.addRenderable(dp, LayerType.Middleground1);

        return dp;
    }

    public Point2D getPosition(){return position;}
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
