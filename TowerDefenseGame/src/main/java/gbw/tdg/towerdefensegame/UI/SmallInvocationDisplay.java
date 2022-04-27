package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SmallInvocationDisplay extends Button {

    private final Invocation invocation;
    private final ARText desc,title;
    private final RenderableImage image;
    private final Font titleFont,descFont;
    private Point2D titleOffset, descOffset, imageOffset;
    private double arcThing = 15;

    public SmallInvocationDisplay(Point2D position, Point2D dim, Clickable root, boolean shouldRenderBackground, Invocation invocation) {
        super(position, dim.getX(),dim.getY(), RText.EMPTY, root, shouldRenderBackground);
        this.invocation = invocation;

        titleFont = Font.font("Impact", Main.canvasSize.getX() * .014);
        descFont = Font.font("Verdana", Main.canvasSize.getY() * .012);

        this.desc = ARText.create(invocation.getDesc(),Point2D.ZERO,1,super.renderingPriority)
                .setDimAR(getDescDim())
                .setFont(descFont)
                .setTextColor(Color.BLACK);

        this.title = ARText.create(invocation.getName(), Point2D.ZERO,4,super.renderingPriority)
                .setDimAR(getImageDim())
                .setFont(titleFont)
                .setTextColor(Color.ORANGE);

        this.image = new RenderableImage(invocation.getImage(),super.renderingPriority,dim,Point2D.ZERO,true);
        this.image.setDimensions(getImageDim());

        setPosition(position);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(super.getBackgroundColor());
        gc.fillRoundRect(position.getX(),position.getY(), getDimensions().getX(), getDimensions().getY(),arcThing,arcThing);

        title.render(gc);
        image.render(gc);
        desc.render(gc);
    }
    @Override
    public void spawn(){
        super.spawn();
        System.out.println("SmallInvocationDisplay.spawn " + invocation.getName());
    }
    @Override
    public void destroy(){
        super.destroy();
        System.out.println("SmallInvocationDisplay.destroy " + invocation.getName());
    }


    private Point2D getTitleOffset(){
        return new Point2D(
                5 * Main.scale.getX() + ((sizeX - getTitleDim().getX()) * 0.5),
                5 * Main.scale.getY() + getTitleDim().getY()
        );
    }
    private Point2D getImageOffset(){
        return new Point2D((
                sizeX - getImageDim().getX()) * .5,
                getTitleOffset().getY() + getTitleDim().getY() + 5 * Main.scale.getY()
        );
    }
    private Point2D getDescOffset(){
        return new Point2D(
                sizeX * .1,
                getTitleOffset().getY() + getImageDim().getY() + 15 * Main.scale.getY()
        );
    }

    private Point2D getTitleDim(){
        return new Point2D(sizeX * 0.75, titleFont.getSize());
    }

    private Point2D getImageDim(){
        return new Point2D(sizeY * (1/3D), sizeY * (1/3D));
    }

    private Point2D getDescDim(){
        return new Point2D(
                sizeX - (getDescOffset().getX() * 2),
                sizeY - (getDescOffset().getY() + 15 * Main.scale.getY())
        );
    }
    public Invocation getInvo(){
        return invocation;
    }

    @Override
    public void setPosition(Point2D p){
        super.setPosition(p);
        title.setPosition(p.add(getTitleOffset())); //2
        image.setPosition(p.add(getImageOffset())); //8
        desc.setPosition(p.add(getDescOffset())); //10
    }
    @Override
    public void setDimensions(Point2D dim){
        super.setDimensions(dim);
        this.setPosition(super.position); //20
        title.setDimensions(getTitleDim()); //0
        image.setDimensions(getImageDim()); //0
        desc.setDimensions(getDescDim()); //20
    }

}
