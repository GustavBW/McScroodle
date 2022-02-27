package gbw.roguelike;

import gbw.roguelike.animationSystem.SpriteAnimator;
import gbw.roguelike.enums.MovementAnimationTypes;
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
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Player extends Damagable implements Renderable, Clickable, Tickable {

    public static FacingDirection facingDirection = FacingDirection.NORTH;
    public static HashMap<MovementAnimationTypes, Double> animationLengthSeconds = new HashMap<>();
    private final static KeyCode[] movementKeys = new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};

    private final Point2D position;
    private final HashMap<BaseStatsType, Double> baseStats;
    private final HashMap<DamageType, Double> resistances;  //As your resistance reaches 1, you take less and less damage of that type
    private final List<KeyCode> currentInputs;
    private final List<InputAbility> abilities;
    private final SpriteAnimator spriteAnimator;
    private long lastIdleCheck;

    public Player(Point2D pos){
        this.position = pos;
        this.baseStats = ContentEngine.getPlayerBaseStats();
        this.abilities = new LinkedList<>();
        this.currentInputs = new LinkedList<>();
        this.resistances = new HashMap<>();

        for(DamageType r : DamageType.values()){
            resistances.put(r, 0.00D);
        }

        this.spriteAnimator = new SpriteAnimator(ContentEngine.getPlayerGraphics(), animationLengthSeconds);
        spriteAnimator.setAnimation(MovementAnimationTypes.WALKING);

        Tickable.tickables.add(this);
        Clickable.clickables.add(this);
    }

    @Override
    public void tick() {

        for(KeyCode input : currentInputs){
            evaluateKeyInput(input);
        }

        if(!currentInputsContainsMovement() && lastIdleCheck + 500_000 < System.nanoTime()){
            spriteAnimator.goIdle(facingDirection);
            lastIdleCheck = System.nanoTime();
        }

        if(baseStats.get(BaseStatsType.HEALTH) <= 0){
            Main.playerIsDead = true;
        }

    }

    private void evaluateKeyInput(KeyCode key){
        double moveSpeed = baseStats.get(BaseStatsType.SPEED_MOVEMENT);
        boolean isMoving = false;

        if (key == movementKeys[0]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(0,1 * moveSpeed);
            facingDirection = FacingDirection.NORTH;
            lastIdleCheck = System.nanoTime();
            isMoving = true;
        }
        if (key == movementKeys[1]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(1 * moveSpeed,0);
            facingDirection = FacingDirection.WEST;
            lastIdleCheck = System.nanoTime();
            isMoving = true;
        }
        if (key == movementKeys[2]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(0,-1 * moveSpeed);
            facingDirection = FacingDirection.SOUTH;
            lastIdleCheck = System.nanoTime();
            isMoving = true;
        }
        if (key == movementKeys[3]) {
            WorldSpace.worldSpaceOffset = WorldSpace.worldSpaceOffset.add(-1 * moveSpeed,0);
            facingDirection = FacingDirection.EAST;
            lastIdleCheck = System.nanoTime();
            isMoving = true;
        }

        if(isMoving){
            spriteAnimator.setAnimation(MovementAnimationTypes.WALKING);
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
        return getRawSize().multiply(Main.playerCanvasScaling);
    }

    public Point2D getRawSize(){
        //Only used to calculate the size of the playerCanvas
        return new Point2D(spriteAnimator.getCurrentFrame().getWidth(), spriteAnimator.getCurrentFrame().getHeight());
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
    public void onClicked() {
        System.out.println("YOU FOUND ME!");
    }


}
