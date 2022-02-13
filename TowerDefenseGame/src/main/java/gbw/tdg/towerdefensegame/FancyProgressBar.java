package gbw.tdg.towerdefensegame;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FancyProgressBar extends ProgressBar implements Tickable{

    private final double sizeX, sizeY;
    private Point2D position;
    private final Color colorBackdrop, color1, color2;
    private double prevCurrent;
    private long lastCall = 0;

    public FancyProgressBar(double sizeX, double sizeY, Point2D position, Color color1, Color color2) {
        super(0, 1, sizeX, true, position);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.position = position;
        colorBackdrop = new Color(0,0,0,0.5);
        this.color1 = color1;
        this.color2 = color2;
        prevCurrent = max;

        Main.addTickable.add(this);
    }

    public void tick(){


        if(System.nanoTime() > lastCall + 500_000_000) {
            if (prevCurrent > current) {
                prevCurrent *= 0.99;
            }
        }

        position = super.getPosition().add(0,-10);

    }

    @Override
    public void render(GraphicsContext gc){

        gc.setFill(colorBackdrop);    //Backdrop
        gc.fillRect(position.getX(), position.getY(), sizeX, sizeY);

        gc.setFill(color1);
        gc.fillRect(position.getX(), position.getY(), sizeX * (current / max), sizeY);

        double bar2Xpos = position.getX() + sizeX * (current / max);
        double bar2Width = sizeX * (prevCurrent / max) - size * (current / max);

        gc.setFill(color2);
        gc.fillRect(bar2Xpos,position.getY(), bar2Width, sizeY);
    }


    @Override
    public void setVal(double i) {
        current = i;
    }
}
