package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.interfaces.Interactible;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class NPC extends GameObject implements Interactible, Renderable {

    private static final double interactionRange = 50D;
    private static boolean anNPCisBeingInteractedWith = false;

    private final ArrayList<String> lines;
    private int lineCount = 0;
    private final int id;
    private Point2D position;
    private NPCInfo info;
    private final Image image;
    private boolean isBeingInteractedWith = false;

    public NPC(int id, Point2D position){
        this.id = id;
        this.info = ContentEngine.getNPCInfo(id);
        this.position = position;
        this.lines = ContentEngine.getNPCLines(id);
        this.image = ContentEngine.getNPCImage(id);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Point2D getSize() {
        return new Point2D(image.getWidth(), image.getHeight());
    }

    @Override
    public boolean isWithinRange(Point2D p) {
        //Locking npcs so that you can only interact with 1 at a time.
        if(anNPCisBeingInteractedWith){
            return false;
        }
        return p.distance(position) < interactionRange;
    }

    @Override
    public void onInteraction() {
        isBeingInteractedWith = true;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
    }
}
