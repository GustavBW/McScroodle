package gbw.roguelike;

import gbw.roguelike.animationSystem.SpriteAnimator;
import gbw.roguelike.enums.AnimationType;
import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.damagingSystem.Damagable;
import gbw.roguelike.enums.BaseStatsType;
import gbw.roguelike.enums.DamageType;
import gbw.roguelike.enums.FacingDirection;
import gbw.roguelike.interfaces.Clickable;
import gbw.roguelike.interfaces.Renderable;
import gbw.roguelike.interfaces.Tickable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.security.Key;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Player extends Damagable implements Renderable, Clickable, Tickable {

    public static HashMap<AnimationType, Double> animationLengthSeconds = new HashMap<>();
    private final static KeyCode[] movementKeys = new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};

    private final Point2D position;
    private final HashMap<BaseStatsType, Double> baseStats;
    private final HashMap<DamageType, Double> resistances;  //As your resistance reaches 1, you take less and less damage of that type
    private final List<KeyCode> currentInputs;
    private final List<InputAbility> abilities;
    private final SpriteAnimator spriteAnimator;
    private FacingDirection facingDirection = FacingDirection.NORTH;

    public Player(Point2D pos){
        this.position = pos;
        this.baseStats = ContentEngine.getPlayerBaseStats();
        this.abilities = new LinkedList<>();
        this.currentInputs = new LinkedList<>();
        this.resistances = new HashMap<>();

        for(DamageType r : DamageType.values()){
            resistances.put(r, 0.00D);
        }

        spriteAnimator = new SpriteAnimator(ContentEngine.getPlayerGraphics(), animationLengthSeconds);
        spriteAnimator.setAnimation(AnimationType.WALKING_NORTH);

        Tickable.tickables.add(this);
        Clickable.clickables.add(this);
    }

    @Override
    public void tick() {

        for(KeyCode input : currentInputs){
            evaluateKeyInput(input);
        }

        if(baseStats.get(BaseStatsType.HEALTH) <= 0){
            Main.playerIsDead = true;
        }

    }

    private void evaluateKeyInput(KeyCode key){
        double moveSpeed = baseStats.get(BaseStatsType.SPEED_MOVEMENT);

        if (key == movementKeys[0]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(0,1 * moveSpeed);
            spriteAnimator.setAnimation(AnimationType.WALKING_NORTH);
            facingDirection = FacingDirection.NORTH;
        }
        if (key == movementKeys[1]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(1 * moveSpeed,0);
            spriteAnimator.setAnimation(AnimationType.WALKING_WEST);
            facingDirection = FacingDirection.WEST;
        }
        if (key == movementKeys[2]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(0,-1 * moveSpeed);
            spriteAnimator.setAnimation(AnimationType.WALKING_SOUTH);
            facingDirection = FacingDirection.SOUTH;
        }
        if (key == movementKeys[3]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(-1 * moveSpeed,0);
            spriteAnimator.setAnimation(AnimationType.WALKING_EAST);
            facingDirection = FacingDirection.EAST;
        }

        if(!currentInputsContainsMovement()){
            System.out.println("Going idle");
            spriteAnimator.goIdle(facingDirection);
        }

        for(InputAbility a : abilities){
            a.evaluate(this, key);
        }
    }

    private boolean currentInputsContainsMovement() {
        boolean toReturn = false;

        for(KeyCode k : currentInputs){

            if(k == KeyCode.W || k == KeyCode.A || k == KeyCode.S || k == KeyCode.D){
                toReturn = true;
            }
        }
        return toReturn;
    }

    public void addInput(KeyCode key) {
        if(!currentInputs.contains(key)){
            currentInputs.add(key);
        }
    }

    public void removeInput(KeyCode key) {
        currentInputs.remove(key);
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(position.getX(), position.getY());
    }

    @Override
    public Point2D getSize() {
        Image bIn = spriteAnimator.getCurrentFrame();
        return new Point2D(bIn.getWidth(),bIn.getHeight());
    }

    @Override
    public void render(GraphicsContext gc) {
        spriteAnimator.render(gc, position);
    }

    @Override
    public double applyDamage(double amount, DamageType type) {
        double damageToTake = checkForResistances(amount, type);
        baseStats.put(BaseStatsType.HEALTH, baseStats.get(BaseStatsType.HEALTH) - damageToTake);

        return damageToTake;
    }

    @Override
    public HashMap<BaseStatsType, Double> getBaseStats() {
        return baseStats;
    }

    private double checkForResistances(double amount, DamageType type){
        return amount * (1 - resistances.get(type));
    }

    @Override
    public boolean isInBounds(Point2D p) {
        Point2D bI1 = this.getSize();
        return (p.getX() < position.getX() + bI1.getX() && p.getX() > position.getX())
                &&
                (p.getY() < position.getY() + bI1.getY() && p.getY() > position.getY());
    }

    @Override
    public void onInteraction() {
        System.out.println("YOU FOUND ME!");
    }


}
