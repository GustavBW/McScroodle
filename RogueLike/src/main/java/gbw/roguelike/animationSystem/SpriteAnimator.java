package gbw.roguelike.animationSystem;

import gbw.roguelike.enums.AnimationType;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public class SpriteAnimator{

    private HashMap<AnimationType, Image[]> animations;
    private HashMap<AnimationType, Double> animationLengths;

    private int currentFrame = 0;
    private int frameLengthOfCurrent = 0;
    private AnimationType currentAnimationType = AnimationType.UNKNOWN;
    private AnimationType previousAnimationType = AnimationType.UNKNOWN;
    private Image[] currentAnimation;
    private double currentAnimationLength = 1;
    private long nsPrFrameOfCurrent = 1;
    private long timestampLastFrame = 0;
    private boolean looping = false;
    private boolean currentHasFinished = false;
    private List<Image[]> queuedAnimations;
    private List<AnimationType> typesOfQueued;

    public SpriteAnimator(HashMap<AnimationType, Image[]> animations, HashMap<AnimationType, Double> animationLengths){
        this.animations = animations;
        this.animationLengths = animationLengths;
        this.currentAnimation = getDefaultIdle();

        this.queuedAnimations = new LinkedList<>();
        this.typesOfQueued = new LinkedList<>();
    }

    public void render(GraphicsContext gc, Point2D pos) {

        if(looping && System.nanoTime() > timestampLastFrame + nsPrFrameOfCurrent){

            System.out.println("Looping");
            gc.drawImage(currentAnimation[(int) (currentFrame % currentAnimationLength)], pos.getX(), pos.getY());
            currentHasFinished = currentFrame == currentAnimationLength;
            currentFrame++;

            if(currentHasFinished && !queuedAnimations.isEmpty()){
                queueGoNext();
            }

        }else if (!looping){

            System.out.println("Not looping");
            gc.drawImage(currentAnimation[0], pos.getX(), pos.getY());

        }
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

    public boolean setAnimation(AnimationType a){
        Image[] newAnim = animations.get(a);

        if(newAnim != null) {
            previousAnimationType = currentAnimationType;

            currentAnimationType = a;
            currentAnimation = newAnim;
            currentAnimationLength = animationLengths.get(a);
            double newLength = animationLengths.get(a);
            looping = newAnim.length > 1;

            nsPrFrameOfCurrent = (long) ((1_000_000_000 * newLength) / newAnim.length);
            return true;
        }
        return false;
    }

    public void queueAnimation(AnimationType a){
        if(animations.get(a) != null) {
            queuedAnimations.add(animations.get(a));
            typesOfQueued.add(a);
        }
    }

    public AnimationType getCurrentAnimationType(){
        return currentAnimationType;
    }

    public AnimationType whatAnimationJustFinished(){
        return previousAnimationType;
    }

    private Image[] getDefaultIdle(){
        Image[] toReturn = new Image[1];

        for(AnimationType a : AnimationType.values()){
            if(animations.get(a) != null){
                toReturn[0] = animations.get(a)[0];
                currentAnimationType = a;
                looping = false;
                break;
            }
        }

        return toReturn;
    }
}
