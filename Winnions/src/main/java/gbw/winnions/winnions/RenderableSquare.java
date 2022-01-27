package gbw.winnions.winnions;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RenderableSquare extends GameObject implements Renderable, Collidable{

    private Point2D position;
    private double health = 10;
    private Point2D[] vertexes;
    private double size, knockbackForce = 10, damage = 10;

    public RenderableSquare(Point2D pos, double size){
        this.position = pos;
        this.size = size;
        vertexes = calcVertexes();
    }


    @Override
    public void render(GraphicsContext gc, Point2D worldSpaceOffset) {

        gc.setFill(Color.BLACK);
        gc.fillRect(worldSpaceOffset.getX() + position.getX() - size / 2, worldSpaceOffset.getY() + position.getY() - size / 2, size,size);

    }

    @Override
    public Point2D getPosition() {
        return new Point2D(position.getX() + size / 2, position.getY() + size / 2);
    }

    @Override
    public boolean isInBounds(Point2D posOCol) {
        return (posOCol.getX() < position.getX() + size && posOCol.getX() > position.getX()) && (posOCol.getY() < position.getY() + size && posOCol.getY() > position.getY());
    }

    @Override
    public void onCollision(Collidable obj) {
        Game.localPlayer.onTerrainCollision();
        health--;
        if(health <= 0){destroy();}
    }

    @Override
    public double getKnockbackForce() {
        return knockbackForce;
    }
    private Point2D[] calcVertexes(){
        return new Point2D[]{
                new Point2D(position.getX(), position.getY()),
                new Point2D(position.getX() + size, position.getY()),
                new Point2D(position.getX(),position.getY() + size),
                new Point2D(position.getX() + size,position.getY() + size)
        };
    }
    @Override
    public Point2D[] getVertexes(){
        return vertexes;
    }

    public void destroy(){
        WorldSpace.removeRenderable(this, LayerType.Background0);
        CollisionHandler.removeCollidable(this);
    }

    @Override
    public String toString(){return "RenderableSquare | " + position.getX() + " | " + position.getY();}
}