package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Decimals;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Renderable;
import gbw.tdg.towerdefensegame.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EnemyStatDisplay implements Renderable, Tickable {

    private double renderingPriority = 56;
    private final RText nameText;
    private final RText hpText;
    private final RText spdText;
    private final Enemy enemy;
    private Color background = new Color(0,0,0,0.5);
    private Point2D position;
    private double sizeX,sizeY, cornerWidth = 15;
    private final EnemyCurrentEffectsDisplay currentEffectsDisplay;

    public EnemyStatDisplay(Enemy e, Point2D position){
        this.nameText = new RText(e.toString(), position.add(60,30),1, Color.RED, Font.font("Impact", 30));
        this.enemy = e;
        this.hpText = new RText("HP: " + enemy.getHp() + "/"+ enemy.getMaxHP(), position.add(85,75),1, Color.WHITE, Font.font("Impact", 25));
        this.spdText = new RText("SPD: " + enemy.getMvspeed(), position.add(85,120),1, Color.WHITE, Font.font("Impact", 25));
        this.position = position;
        this.sizeX = Main.canvasSize.getX() * 0.1;
        this.sizeY = Main.canvasSize.getY() * 0.1;

        this.currentEffectsDisplay = new EnemyCurrentEffectsDisplay(
                new Point2D(position.getX(), position.getY() + sizeY),
                renderingPriority,e);
    }

    public void render(GraphicsContext gc){
        gc.setFill(background);
        gc.fillRoundRect(position.getX(), position.getY(), sizeX, sizeY,cornerWidth,cornerWidth);

        nameText.render(gc);
        hpText.render(gc);
        spdText.render(gc);
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
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
    }

    public void setPosition(Point2D p){
        nameText.setPosition(p);
    }
    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }

    @Override
    public Point2D getDimensions() {
        return new Point2D(sizeX, sizeY);
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        Tickable.newborn.add(this);
        currentEffectsDisplay.spawn();
    }
    @Override
    public void destroy() {
        Renderable.expended.add(this);
        Tickable.expended.add(this);
        currentEffectsDisplay.destroy();
    }


    @Override
    public void tick() {
        hpText.setText("HP: " + Decimals.toXDecimalPlaces(enemy.getHp(),2) + "/"+ enemy.getMaxHP());
        spdText.setText("SPD: " + Decimals.toXDecimalPlaces(enemy.getMvspeed(),2));
    }
}
