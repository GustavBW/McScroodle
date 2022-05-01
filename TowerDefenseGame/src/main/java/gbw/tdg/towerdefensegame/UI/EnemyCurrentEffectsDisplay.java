package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.Tickable;
import gbw.tdg.towerdefensegame.enemies.Enemy;
import gbw.tdg.towerdefensegame.augments.LifetimeEffect;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class EnemyCurrentEffectsDisplay implements Tickable {

    private Point2D position;
    private double rendPrio;
    private Enemy enemy;
    private double sizeX,sizeY,cornerWidth = 15;
    private final Color background = new Color(0,0,0,0.5);
    private GraphicalInventory<ARText> menu;

    public EnemyCurrentEffectsDisplay(Point2D pos, double rendPrio, Enemy enemy){
        this.position = pos;
        this.rendPrio = rendPrio;
        this.enemy = enemy;
        this.sizeX = Main.canvasSize.getX() * 0.1;
        this.sizeY = Main.canvasSize.getY() * 0.1;
        this.menu = new GraphicalInventory<>(1,sizeX,sizeY, 5,pos,rendPrio,4);
    }

    @Override
    public void tick() {
        menu.setInventory(getEffectTexts());
    }

    private List<ARText> getEffectTexts() {
        List<ARText> texts = new ArrayList<>();

        for(LifetimeEffect lE : enemy.getLifetimeEffects()){
            texts.add(
                    ARText.create(lE.getEffectString(),Point2D.ZERO,2,0)
                            .setFont(Font.font("Impact",Main.canvasSize.getX() * 0.01))
                            .setBackgroundColor(new Color(0,Main.random.nextDouble(),0,1))
                            .setCornerWidth(Main.canvasSize.getX() * 0.001)
                            .setTextColor2(new Color(0,Main.random.nextDouble(),0,0.5))
            );
        }

        return texts;
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
        menu.spawn();
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
        menu.destroy();
    }


}
