package gbw.roguelike;

import gbw.roguelike.animationSystem.SpriteAnimator;
import gbw.roguelike.enums.AnimationType;
import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.damagingSystem.Damagable;
import gbw.roguelike.enums.BaseStatsType;
import gbw.roguelike.enums.DamageType;
import gbw.roguelike.enums.ResistanceType;
import gbw.roguelike.interfaces.Clickable;
import gbw.roguelike.interfaces.Renderable;
import gbw.roguelike.interfaces.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Player extends Damagable implements Renderable, Clickable, Tickable {

    public static HashMap<AnimationType, Double> animationLengthSeconds = new HashMap<>();

    private final Point2D position;

    private final HashMap<BaseStatsType, Double> baseStats;
    private final HashMap<ResistanceType, Double> resistances;  //As your resistance reaches 1, you take less and less damage of that type
    private final SpriteAnimator spriteAnimator;

    public Player(Point2D pos){
        this.position = pos;
        this.baseStats = new HashMap<>();

        this.resistances = new HashMap<>();

        for(BaseStatsType b : BaseStatsType.values()){
            baseStats.put(b, 1.00D);
        }
        for(ResistanceType r : ResistanceType.values()){
            resistances.put(r, 0.00D);
        }
        for(AnimationType a : AnimationType.values()){
            animationLengthSeconds.put(a,1.00D);
        }

        spriteAnimator = new SpriteAnimator(ContentEngine.getPlayerGraphics(), animationLengthSeconds);

    }

    @Override
    public void tick() {

    }

    @Override
    public Point2D getPosition() {
        return null;
    }

    @Override
    public void render(GraphicsContext gc) {
        spriteAnimator.render(gc, position);
    }

    @Override
    public double applyDamage(double amount, DamageType type) {
        baseStats.put(BaseStatsType.HEALTH, baseStats.get(BaseStatsType.HEALTH) - checkForResistances(amount, type));

        return 0;
    }

    @Override
    public HashMap<BaseStatsType, Double> getBaseStats() {
        return baseStats;
    }

    private double checkForResistances(double amount, DamageType type){

        for(ResistanceType r : ResistanceType.values()){
            if(r.index == type.index){
                amount *= 1 - resistances.get(r);
            }
        }

        return amount;
    }

    @Override
    public boolean isInBounds(Point2D p) {
        return false;
    }

    @Override
    public void onInteraction() {

    }
}
