package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.OnScreenWarning;
import gbw.tdg.towerdefensegame.handlers.KeyHandler;
import javafx.scene.input.KeyCode;

public class DevTools implements Tickable{

    private boolean spawned = false;

    @Override
    public void spawn() {
        spawned = true;
        new OnScreenWarning("Dev Tools Enabled", Main.canvasSize.multiply(0.4),5).spawn();
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        spawned = false;
        new OnScreenWarning("Dev Tools Disabled", Main.canvasSize.multiply(0.4),5).spawn();
        Tickable.expended.add(this);
    }

    public void toggleExistence(){
        if(spawned){
            destroy();
        }else{
            spawn();
        }
    }

    @Override
    public void tick() {
        if(KeyHandler.currentInputs.contains(KeyCode.M) && KeyHandler.currentInputs.contains(KeyCode.L)){
            Main.alterGoldAmount(100);
        }
    }
}
