package gbw.roguelike.animationSystem;

import gbw.roguelike.enums.MovementAnimationTypes;
import gbw.roguelike.enums.FacingDirection;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public class SpriteAnimator{

    private HashMap<MovementAnimationTypes, Image[]> animations;
    private HashMap<MovementAnimationTypes, Double> animationLengths;

    private int currentFrameNumber = 0;
    private int frameLengthOfCurrent = 0;
    private MovementAnimationTypes currentAnimationType = MovementAnimationTypes.UNKNOWN;
    private MovementAnimationTypes previousAnimationType = MovementAnimationTypes.UNKNOWN;
    private Image[] currentAnimation;
    private double currentAnimationLengthSeconds = 1;
    private int currentAnimationLengthFrames = 1;
    private Image currentFrame;
    private long nsPrFrameOfCurrent = 1;
    private long timestampLastFrame = 0;
    private boolean looping = false;
    private boolean currentHasFinished = false;
    private List<Image[]> queuedAnimations;
    private List<MovementAnimationTypes> typesOfQueued;

    public SpriteAnimator(HashMap<MovementAnimationTypes, Image[]> animations, HashMap<MovementAnimationTypes, Double> animationLengths){
        this.animations = animations;
        this.animationLengths = animationLengths;
        this.currentAnimation = getDefaultIdle();

        this.queuedAnimations = new LinkedList<>();
        this.typesOfQueued = new LinkedList<>();
    }

    public void render(GraphicsContext gc, Point2D pos) {

        if(looping && System.nanoTime() > timestampLastFrame + nsPrFrameOfCurrent){

            currentFrame = currentAnimation[currentFrameNumber % currentAnimationLengthFrames];

            currentHasFinished = currentFrameNumber == currentAnimationLengthFrames;

            timestampLastFrame = System.nanoTime();
            currentFrameNumber++;

            if(currentHasFinished && !queuedAnimations.isEmpty()){
                queueGoNext();
            }

        }else if (!looping){

            currentFrame = currentAnimation[0];

        }
        gc.drawImage(currentFrame, pos.getX(), pos.getY());
    }

    private void queueGoNext() {
        queuedAnimations.remove(currentAnimation);
        typesOfQueued.remove(currentAnimationType);

        if(!queuedAnimations.isEmpty()){

            setAnimation(typesOfQueued.get(0));

        }else{
            currentAnimation = getDefaultIdle();
        }
    }

    public boolean setAnimation(MovementAnimationTypes aType){
        Image[] newAnim = animations.get(aType);

        if(newAnim != null && aType != currentAnimationType) {
            System.out.println("Changed animation upon behest from the player to: | " + aType);
            previousAnimationType = currentAnimationType;

            currentAnimationType = aType;
            currentAnimation = newAnim;
            currentAnimationLengthSeconds = animationLengths.get(aType);
            currentAnimationLengthFrames = newAnim.length;
            looping = newAnim.length > 1;

            nsPrFrameOfCurrent = (long) ((1_000_000_000 * currentAnimationLengthSeconds) / currentAnimationLengthFrames);
            currentFrameNumber = 0;
            return true;
        }
        return false;
    }

    public boolean goIdle(FacingDirection dir){
        MovementAnimationTypes asAnimType = getFacingDirectionAsAnimType(dir);
        Image[] defaultIdleAnim = animations.get(asAnimType);
        Image[] requstedIdle = null;

        if(defaultIdleAnim != null){
            previousAnimationType = currentAnimationType;

            currentAnimationType = asAnimType;
            currentAnimation = new Image[]{defaultIdleAnim[0]};
            currentAnimationLengthSeconds = animationLengths.get(asAnimType);
            currentAnimationLengthFrames = 1;
            looping = false;

            nsPrFrameOfCurrent = (long) ((1_000_000_000 * currentAnimationLengthSeconds) / currentAnimationLengthFrames);
            currentFrameNumber = 0;
            return true;
        }

        return false;
    }

    public static MovementAnimationTypes getFacingDirectionAsAnimType(FacingDirection dir) {
        for(MovementAnimationTypes a : MovementAnimationTypes.values()){
            if(a.direction == dir){
                return a;
            }
        }

        return null;
    }

    public void queueAnimation(MovementAnimationTypes a){
        if(animations.get(a) != null) {
            queuedAnimations.add(animations.get(a));
            typesOfQueued.add(a);
        }
    }

    public MovementAnimationTypes getCurrentAnimationType(){
        return currentAnimationType;
    }

    public MovementAnimationTypes whatAnimationJustFinished(){
        return previousAnimationType;
    }

    private Image[] getDefaultIdle(){
        return animations.get(MovementAnimationTypes.IDLE);
    }

    public Image getCurrentFrame(){
        return currentFrame == null ? getDefaultIdle()[0] : currentFrame;
    }
}
