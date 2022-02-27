package gbw.roguelike;

import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.interfaces.Clickable;
import gbw.roguelike.interfaces.Interactible;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item implements Clickable, Renderable, Interactible {

    protected Point2D position;
    protected double interactionRange = 50;
    protected String name;
    private Image image;
    protected int id;

    public Item(Point2D position, String name, int amount, int id){
        this.name = name;
        this.position = position;
        this.id = id;
        this.image = ContentEngine.getItemGraphics(id);
    }

    public Item(Point2D position, String name, int id){
        this(position, name, 1,id);
    }

    @Override
    public boolean isInBounds(Point2D p) {
        return false;
    }

    @Override
    public boolean isWithinRange(Point2D p) {
        return false;
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void onClicked() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
