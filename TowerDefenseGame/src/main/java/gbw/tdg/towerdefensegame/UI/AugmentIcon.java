package gbw.tdg.towerdefensegame.UI;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AugmentIcon extends RenderableImage implements Clickable {

    private Clickable root;
    private boolean isSpawned = false;

    public AugmentIcon(Image image, double rendPrio, Point2D dim, Point2D position, boolean fixedScaling) {
        super(image, rendPrio, dim, position, fixedScaling);
    }

    @Override
    public void spawn() {
        if(!isSpawned) {
            Clickable.newborn.add(this);
            super.spawn();
            isSpawned = true;
        }
    }

    @Override
    public void destroy() {
        Clickable.expended.add(this);
        super.destroy();
        isSpawned = false;
    }

    @Override
    public void render(GraphicsContext gc){
        super.render(gc);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() >= position.getX() && pos.getX() <= position.getX() + dim.getX())
                &&
                (pos.getY() >= position.getY() && pos.getY() <= position.getY() + dim.getY());
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void deselect() {
        destroy();
    }

    @Override
    public Clickable getRoot() {
        return root;
    }

    public AugmentIcon setRoot(Clickable root){
        this.root = root;
        return this;
    }
}
