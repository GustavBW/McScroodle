package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.augments.Augment;
import gbw.tdg.towerdefensegame.tower.Tower;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class TowerStatDisplay implements Renderable, Tickable, Clickable {

    private double renderingPriority = 57;
    private final RText text;
    private final Tower tower;
    private Color background = new Color(0,0,0,0.5);
    private Point2D position;
    private double sizeX,sizeY, cornerWidth = 15;
    private Menu<AugmentIcon> augmentDisplay;
    private long lastCall;
    private boolean isSpawned = false;

    public TowerStatDisplay(Tower t, Point2D position){
        this.text = new RText(t.getStats(),
                position.add(Main.canvasSize.getX() * 0.015,Main.canvasSize.getX() * 0.007),
                1, Color.WHITESMOKE, Font.font("Impact", Main.canvasSize.getX() * 0.0104));
        this.tower = t;
        this.position = position;
        this.sizeY = Main.canvasSize.getY() * 0.1;

        double posXOfAugmentDisplay = Main.canvasSize.getX() * 0.1 - (sizeY / 3);
        this.sizeX = Main.canvasSize.getX() * 0.1 - (sizeY / 3);
        this.augmentDisplay = new Menu<>(position.add(posXOfAugmentDisplay,0),new Point2D(sizeX,sizeY),renderingPriority +1,1,3);
    }

    public void render(GraphicsContext gc){
        gc.setFill(background);
        gc.fillRoundRect(position.getX(), position.getY(), sizeX, sizeY,cornerWidth,cornerWidth);

        text.render(gc);
    }

    @Override
    public void tick(){
        if(System.currentTimeMillis() >= lastCall + 1000) {
            text.setText(tower.getStats());

            List<AugmentIcon> currentAugImages = new ArrayList<>();
            for (Augment a : tower.getAugments()) {
                currentAugImages.add(a.getIcon().setRoot(this.getRoot()));
            }
            augmentDisplay.clear();
            augmentDisplay.addAll(currentAugImages);
            lastCall = System.currentTimeMillis();
        }
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setRenderingPriority(double newPrio){this.renderingPriority = newPrio;}
    public void setPosition(Point2D p){
        text.setPosition(p);
    }
    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }
    @Override
    public void spawn() {
        if(!isSpawned) {
            Renderable.newborn.add(this);
            Tickable.newborn.add(this);
            augmentDisplay.spawn();
            isSpawned = true;
        }
    }
    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
        augmentDisplay.destroy();
        isSpawned = false;
    }

    public Point2D getExtremeties() {
        return new Point2D(
                position.getX() + sizeX,
                position.getY() + sizeY
        );
    }

    public void setText(String newText) {
        text.setText(newText);
    }

    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() >= position.getX() && pos.getX() <= position.getX() + sizeX)
                &&
                (pos.getY() >= position.getY() && pos.getY() <= position.getY() + sizeY);
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void deselect() {
        destroy();
    }

    @Override
    public Clickable getRoot(){
        return tower;
    }
}
