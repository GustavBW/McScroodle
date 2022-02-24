package gbw.roguelike;

import javafx.geometry.Point2D;

public class LevelExitTrigger extends Trigger{

    private int levelToChangeTo;

    public LevelExitTrigger(Point2D position, double range, int newLevel){
        this.levelToChangeTo = newLevel;
        this.range = range;
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public boolean isInBounds(Point2D p) {
        double distX = p.getX() - position.getX();
        double distY = p.getY() - position.getY();
        double distSquared = distX * distX + distY * distY;

        return distSquared < range * range;
    }

    @Override
    public void onInteraction() {
        System.out.println("You interacted with a LevelExitTrigger");
        GamePathGenerator.setlevel(levelToChangeTo);
    }
}
