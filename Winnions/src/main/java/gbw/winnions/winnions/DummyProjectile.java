package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DummyProjectile extends GameObject implements Renderable, Tickable, Collidable {

    private final double bulletspeed = 10, lifetime = 2000, size = 15, knockbackForce = 10;
    private long startTime = System.currentTimeMillis();
    private Point2D position, velocity;
    private Player owner;
    private Point2D[] vertexes;

    public DummyProjectile(Point2D pos, Point2D vel, Player p1) {
        this.position = pos;

        this.velocity = vel.normalize();
        velocity = velocity.multiply(bulletspeed);

        this.owner = p1;
        vertexes = calcVertexes();
    }

    @Override
    public void render(GraphicsContext gc, Point2D worldSpaceOffset) {
        gc.setFill(Color.RED);
        gc.fillRoundRect(worldSpaceOffset.getX() + position.getX(), worldSpaceOffset.getY() + position.getY(), size, size, size, size);
    }

    @Override
    public void tick() {
        position = position.add(velocity);

        if (System.currentTimeMillis() >= startTime + lifetime || position.getX() > WorldSpace.worldDimensions.getX() || position.getX() < 0 || position.getY() > WorldSpace.worldDimensions.getY() || position.getY() < 0) {
            destroy();
        }
    }

    public void destroy() {
        WorldSpace.removeRenderable(this, LayerType.Middleground1);
        TickHandler.removeTickable(this);
        CollisionHandler.removeCollidable(this);
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public boolean isInBounds(Point2D posOCol) {
        return (posOCol.getX() < position.getX() + size && posOCol.getX() > position.getX()) && (posOCol.getY() < position.getY() + size && posOCol.getY() > position.getY());
    }

    @Override
    public void onCollision(Collidable obj) {
        if(owner != Game.localPlayer) {
            Game.localPlayer.changeHealth(-10);
            destroy();
        }

    }

    private Point2D[] calcVertexes(){
        return new Point2D[]{
                new Point2D(0,0),
                new Point2D(size, 0),
                new Point2D(0,size),
                new Point2D(size,size)
        };
    }
    @Override
    public Point2D[] getVertexes(){
        Point2D[] updatedVerts = vertexes.clone();
        for(Point2D p : updatedVerts){
            p.add(position);
        }
        return updatedVerts;
    }

    @Override
    public double getKnockbackForce() {
        return knockbackForce;
    }
    @Override
    public String toString(){return "DummyProjectile " + position.getX() + " | " + position.getY();}
}
