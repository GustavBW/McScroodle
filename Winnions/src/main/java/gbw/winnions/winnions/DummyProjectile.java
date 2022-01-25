package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DummyProjectile implements Renderable, Tickable{

    private final double bulletspeed = 10, lifetime = 2000;
    private long startTime = System.currentTimeMillis();
    private Point2D position, velocity;
    private Player owner;

    public DummyProjectile(Point2D pos, Point2D vel, Player p1){
        this.position = pos;

        this.velocity = vel.normalize();
        velocity = velocity.multiply(bulletspeed);

        this.owner = p1;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRoundRect(position.getX(),position.getY(),15,15,15,15);
    }

    @Override
    public void tick() {
        position = position.add(velocity);

        if(System.currentTimeMillis() >= startTime + lifetime){
            TickHandler.removeTickable(this);
            Game.removeRenderable(this);
        }
    }


    public Player getOwner(){return owner;}
}
