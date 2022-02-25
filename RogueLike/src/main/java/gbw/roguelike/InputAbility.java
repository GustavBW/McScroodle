package gbw.roguelike;

import gbw.roguelike.GameObject;
import gbw.roguelike.interfaces.Tickable;
import javafx.scene.input.KeyCode;

public abstract class InputAbility implements Tickable {

    protected boolean isActive = true;
    protected long isActiveAgainAt = 0;
    protected long isDeactivatedAgainAt = 0;

    public abstract void activate(boolean isTimed, long duration);
    public abstract void deactivate(boolean isTimed, long duration);

    public abstract void evaluate(GameObject g, KeyCode key);
}
