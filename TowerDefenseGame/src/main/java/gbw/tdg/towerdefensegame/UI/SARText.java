package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.backend.TextFormatter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class SARText extends ARText {
    //Stacked Advanced Renderable Text

    private Point2D dim;
    private List<String> lines;
    private Font font;
    private double yAdvance, margin = 5, prefferedFontSize = 20;
    private boolean isFitted = true;

    protected SARText(List<String> lines, Point2D position, Point2D dim, double dropShadowOffset, Font font, double rendPrio) {
        super("", position, dropShadowOffset, font, rendPrio);
        this.dim = dim;
        this.lines = new ArrayList<>(lines);
        this.font = getFittedFont(font);
        this.yAdvance = getYAdvance(dim,font);
    }

    public static SARText create(List<String> lines, Point2D position, Point2D dim, double dropShadowOffset, double rendPrio){
        return new SARText(lines,position,dim,dropShadowOffset, RText.STANDARD_FONT,rendPrio);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFont(font);
        for(int i = 0; i < lines.size(); i++){
            gc.fillText(lines.get(i), position.getX(), position.getY() + (i * yAdvance), dim.getX());
        }
    }

    public SARText setDimSAR(Point2D dim){
        this.dim = dim;
        return this;
    }
    public SARText setMargin(double margin){
        this.margin = margin;
        return this;
    }

    public SARText setFont(Font font){
        this.font = getFittedFont(font);
        return this;
    }

    public SARText setFittingFontSize(boolean state){
        this.isFitted = state;
        return this;
    }

    public SARText setPrefferedFontSize(double prefSize){
        this.prefferedFontSize = prefSize;
        return this;
    }

    @Override
    public void setText(String s){
        lines = new ArrayList<>(TextFormatter.toLinesArray(s,(int) (dim.getX() / font.getSize()),"\n"));
    }
    public SARText setText(List<String> l){
        this.lines = l;
        return this;
    }
    @Override
    public String getText(){
        return TextFormatter.concatonateArray(lines);
    }
    public List<String> getLines(){
        return new ArrayList<>(lines);
    }

    @Override
    public void setDimensions(Point2D dim){
        this.dim = dim;
        this.font = getFittedFont(font);
        this.yAdvance = getYAdvance(dim,font);
    }

    private double getYAdvance(Point2D dim, Font font) {
        return getFittedFontSize() + margin;
    }

    private Font getFittedFont(Font font){
        double fontSizeToUse = isFitted ? Math.min(getFittedFontSize(), prefferedFontSize) : getFittedFontSize();
        return Font.font(font.getFamily(),fontSizeToUse);
    }

    private double getFittedFontSize(){
       return (dim.getY() / lines.size()) - (margin * (lines.size() -1));
    }
}
