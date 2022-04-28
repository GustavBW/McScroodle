package gbw.tdg.towerdefensegame.UI.vfx;

import gbw.tdg.towerdefensegame.Main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TopDownStrikeVFX extends VFX{

    private Image image;
    private boolean rendersImage;
    private double imageHeight;
    private double imageWidth;

    public TopDownStrikeVFX(int lifetime, double rendPrio, Point2D position) {
        super(lifetime, rendPrio);
        super.position = position;
        super.dim = new Point2D(5,5);
    }

    public TopDownStrikeVFX(int lifetime, double rendPrio, Point2D position, Image image){
        this(lifetime,rendPrio,position);
        if(image == null){return;}

        this.image = image;
        this.rendersImage = true;
        imageWidth = image.getWidth() * Main.scale.getX();
        imageHeight = image.getHeight() * Main.scale.getY();
        this.position = new Point2D(
                position.getX() - (imageWidth * 0.5),
                position.getY() - imageHeight
        );
    }

    @Override
    public void render(GraphicsContext gc){
        if(rendersImage) {
            gc.drawImage(image, position.getX(), position.getY(), imageWidth,imageHeight);
        }else{
            gc.setFill(Color.AQUA);
            gc.fillRect(position.getX() - dim.getX(), 0, dim.getX() * 2, position.getY());
        }
    }
}
