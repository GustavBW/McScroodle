package gbw.roguelike.ui;

import gbw.roguelike.Main;
import gbw.roguelike.handlers.SceneManager;
import gbw.roguelike.interfaces.Clickable;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StartMenuButton extends Button implements Clickable, Renderable {

    private final TextElement textElement;
    private final Color backgroundColor;

    public StartMenuButton(Point2D pos, Point2D size, TextElement t){
        super.position = pos;
        super.size = size;

        backgroundColor = new Color(0,0,0,0.5);

        this.textElement = t;
        t.setPosition(pos.add(size.getX() * 0.2, size.getY() * 0.5));
    }

    @Override
    public void onInteraction(){
        SceneManager.changeScene(new StartMenuScene());
        Main.stopInGameUpdates();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRoundRect(position.getX(), position.getY(), size.getX(),size.getY(),size.getX() * 0.1, size.getY() * 0.1);

        textElement.render(gc);
    }

    @Override
    public void instantiate(){
        Clickable.clickables.add(this);
    }

    @Override
    public void destroy(){
        Clickable.clickables.remove(this);
    }
}
