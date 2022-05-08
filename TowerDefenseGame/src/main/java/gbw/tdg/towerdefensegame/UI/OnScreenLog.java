package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnScreenLog implements Renderable, Tickable {

    private List<Message> messages, filteredMSGs;
    private Point2D position, dimensions;
    private final Font font = Font.font("Impact", 60 * Main.scale.getX());
    private final RText printerHead = new RText("", Point2D.ZERO, 6, Color.WHITE,font,false);
    private final double[] opacityGradient = {1,.8,.6,.4,.2};
    private final int msgAtATime = 5;

    public OnScreenLog(Point2D position, Point2D dimensions){
        this.position = position;
        this.dimensions = dimensions;
        this.messages = new ArrayList<>();
        this.filteredMSGs = new ArrayList<>();
        Color c1 = new Color(1,1,1,1);
        Color c2 = new Color(c1.getRed(),c1.getGreen(), c1.getBlue(), c1.getOpacity());
    }

    public void add(Message msg){
        msg.reset();
        messages.add(0,msg);
        trim(messages,msgAtATime);
    }

    private void trim(List<?> list, int sizeWanted){
        while(list.size() > sizeWanted){
            list.remove(list.size()-1);
        }
    }

    @Override
    public void tick() {
        filteredMSGs = messages.stream().filter(Objects::nonNull).toList();

        for(Message m : filteredMSGs){
            if(m.timeout()){
                messages.remove(m);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for(int i = 0; i < filteredMSGs.size(); i++){
            Message current = filteredMSGs.get(i);
            printerHead.setTextColor(getAlteredColor(current.getColor(), i));
            movePrinterHead(i,current);
            printerHead.render(gc);
        }
    }

    private Color getAlteredColor(Color c1, int num){
        return new Color(
                c1.getRed(),c1.getGreen(),c1.getBlue(),
                opacityGradient[Math.min(opacityGradient.length - 1,num)]
        );
    }

    private void movePrinterHead(int num, Message msg){
        printerHead.setText(msg.text);
        printerHead.setPosition(new Point2D(
                position.getX() + ((dimensions.getX() - TextFormatter.getWidthOf(printerHead)) * .5),
                position.getY() - (num * font.getSize())
        ));
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return 99;
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }

    @Override
    public void setRenderingPriority(double newPrio) {

    }

    @Override
    public void setDimensions(Point2D dim) {
        this.dimensions = dim;
    }

    @Override
    public Point2D getDimensions() {
        return dimensions;
    }


}
