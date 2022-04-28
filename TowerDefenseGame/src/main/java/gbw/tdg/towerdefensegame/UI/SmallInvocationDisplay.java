package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.backend.Point2G;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import gbw.tdg.towerdefensegame.invocation.Invocation;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class SmallInvocationDisplay extends Button {

    private final Invocation invocation;
    private final ARText desc,title;
    private final RenderableImage image;
    private final Font titleFont,descFont;
    private Point2D titleOffset, descOffset, imageOffset;
    private double arcThing = 15;
    private final Color color = new Color(0,0,0,0.5);
    private final Color color2 = new Color(1,1,1,0.8);

    public SmallInvocationDisplay(Point2D position, Point2D dim, Clickable root, boolean shouldRenderBackground, Invocation invocation) {
        super(position, dim.getX(),dim.getY(), RText.EMPTY, root, shouldRenderBackground);
        this.invocation = invocation;

        titleFont = Font.font("Impact", Main.canvasSize.getX() * .014);
        descFont = Font.font("Verdana", Main.canvasSize.getY() * .012);

        this.desc = ARText.create(getFormattedDesc(),Point2D.ZERO,1,super.renderingPriority)
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
        gc.setFill(color);
        gc.fillRoundRect(position.getX(),position.getY(), getDimensions().getX(), getDimensions().getY(),arcThing,arcThing);

        Point2D descPos = getDescBackgroundPos();
        Point2D descDim = getDescRimDim();

        gc.setFill(color2);
        gc.fillRoundRect(descPos.getX(),descPos.getY(),descDim.getX(),descDim.getY(),arcThing,arcThing);


        title.render(gc);
        image.render(gc);
        desc.render(gc);
    }

    private String getFormattedDesc(){
        List<String> fittedDesc = TextFormatter.wordWrapCustom(invocation.getLongDesc(), descFont,getDescDim().getX());
        return TextFormatter.concatonateArray(fittedDesc, "\n");
    }

    @Override
    public void spawn(){
        super.spawn();
    }
    @Override
    public void destroy(){
        super.destroy();
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
                getTitleOffset().getY() + 15 * Main.scale.getY()
        );
    }
    private Point2D getDescOffset(){
        return new Point2D(
                sizeX * .1,
                getImageOffset().getY() + getImageDim().getY() + 30 * Main.scale.getY() + descFont.getSize()
        );
    }
    private Point2D getDescBackgroundPos(){
        Point2D descOffset = getDescOffset();
        return position.add(
                descOffset.getX() - 15 * Main.scale.getX(),
                descOffset.getY() - (descFont.getSize() + 15 * Main.scale.getY()));
    }
    private Point2D getDescRimDim(){
        Point2D descDim = getDescDim();
        return new Point2D(
                descDim.getX() + 30 * Main.scale.getY(),
                descDim.getY() + 30 * Main.scale.getY()
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
                sizeY - (getDescOffset().getY() + 30 * Main.scale.getY())
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
