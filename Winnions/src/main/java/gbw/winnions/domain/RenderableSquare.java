package gbw.winnions.domain;

import gbw.winnions.Game;
import gbw.winnions.presentation.Renderable;
import gbw.winnions.presentation.WorldSpace;
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
    public void render(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(WorldSpace.currentWorldSpaceOffset.getX() + position.getX() - size / 2, WorldSpace.currentWorldSpaceOffset.getY() + position.getY() - size / 2, size,size);

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
        Point2D[] updatedVerts = new Point2D[vertexes.length];

        for(int i = 0; i < updatedVerts.length; i++){
           updatedVerts[i] = new Point2D(vertexes[i].getX() + WorldSpace.currentWorldSpaceOffset.getX(), vertexes[i].getY() + WorldSpace.currentWorldSpaceOffset.getY());
        }

        return updatedVerts;
    }

    public void destroy(){
        WorldSpace.removeRenderable(this, LayerType.Background0);
        CollisionHandler.removeCollidable(this);
    }

    @Override
    public String toString(){return "RenderableSquare | " + position.getX() + " | " + position.getY();}
}
