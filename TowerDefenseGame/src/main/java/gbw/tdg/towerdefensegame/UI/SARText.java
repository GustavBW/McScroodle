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
    public static SARText create(String plainText, Point2D position, Point2D dim, double dropShadowOffset, double rendPrio){
        int dimT = dim.getX() > RText.STANDARD_FONT.getSize() ? (int) dim.getX() : (int)  RText.STANDARD_FONT.getSize();
        dim = new Point2D(dimT, dim.getY());
        return new SARText(TextFormatter.wordWrapCustom(plainText,RText.STANDARD_FONT, dimT),position,dim,dropShadowOffset, RText.STANDARD_FONT,rendPrio);
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
        recalc();
        return this;
    }
    public SARText setMargin(double margin){
        this.margin = margin;
        recalc();
        return this;
    }

    public SARText setFont(Font font){
        this.font = getFittedFont(font);
        recalc();
        return this;
    }

    public SARText setFittingFontSize(boolean state){
        boolean prevState = isFitted;
        this.isFitted = state;
        if(prevState != isFitted){
            recalc();
        }
        return this;
    }

    public SARText setPrefferedFontSize(double prefSize){
        this.prefferedFontSize = prefSize;
        recalc();
        return this;
    }

    @Override
    public void setText(String s){
        lines = new ArrayList<>(TextFormatter.wordWrapCustom(s,font,(int) dim.getX()));
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
        recalc();
    }

    private void recalc(){
        this.font = getFittedFont(font);
        this.yAdvance = getYAdvance(dim, font);
        this.lines = TextFormatter.wordWrapCustom(TextFormatter.concatonateArray(lines, " "),font,(int) dim.getX());
    }

    private double getYAdvance(Point2D dim, Font font) {
        return getFittedFontSize() + margin;
    }

    private Font getFittedFont(Font font){
        double fontSizeToUse = isFitted ? Math.min(getFittedFontSize(), prefferedFontSize) : getFittedFontSize();
        return Font.font(font.getFamily(),fontSizeToUse);
    }

    private double getFittedFontSize(){
        int lineSize = lines.isEmpty() ? 1 : lines.size();
        return (dim.getY() / lineSize) - (margin * (lineSize -1));
    }
}
