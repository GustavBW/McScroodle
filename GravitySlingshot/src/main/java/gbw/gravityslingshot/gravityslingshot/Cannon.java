package gbw.gravityslingshot.gravityslingshot;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Cannon implements Tickable, Renderable {

    private double angle;
    private final Point2D position;

    public Cannon(double angle, Point2D position) {
        this.angle = angle;
        this.position = position;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BROWN);
        gc.fillRoundRect(position.getX(),position.getY(),40,40,20,20);
    }

    @Override
    public void tick() {
        //point towards the mouse

        if(KeyInputHandler.currentlyActive.contains(KeyCode.SPACE)){
            shoot();
        }
    }

    private void shoot(){
        Point2D velocity = position.subtract(MouseInputHandler.mousePosition);
        Bullet bullet = new Bullet(position, velocity, 20);
        bullet.spawn();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
        Renderable.expended.add(this);
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
    }
}
