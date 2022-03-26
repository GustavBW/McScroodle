package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Goal implements Renderable, Collidable{

    private Point2D position;
    private double size = 30;

    public Goal(Point2D position){
        this.position = position;
    }

    @Override
    public boolean isInBounds(Point2D p) {
        double distance = position.distance(p);
        return distance < size;
    }

    @Override
    public void onCollisionWith(Collidable c) {
        if(c instanceof Bullet){
            System.out.println("Goal hit!");
        }
    }

    @Override
    public Point2D getPosition() {
        return null;
    }

    @Override
    public void destroy() {
        Collidable.expended.add(this);
        Renderable.expended.add(this);
    }

    @Override
    public void spawn() {
        Collidable.newborn.add(this);
        Renderable.newborn.add(this);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(position.getX(),position.getY(),size,size,size,size);
    }
}
