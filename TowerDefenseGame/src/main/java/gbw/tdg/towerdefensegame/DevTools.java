package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Message;
import gbw.tdg.towerdefensegame.UI.OnScreenWarning;
import gbw.tdg.towerdefensegame.UI.scenes.InGameScreen;
import gbw.tdg.towerdefensegame.handlers.KeyHandler;
import javafx.scene.input.KeyCode;

public class DevTools implements Tickable{

    private boolean spawned = false;

    @Override
    public void spawn() {
        spawned = true;
        InGameScreen.informationLog.add(new Message("Dev Tools Enabled",3_000));
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        spawned = false;
        InGameScreen.informationLog.add(new Message("Dev Tools Disabled",3_000));
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
            Main.alterSoulsAmount(10);
        }
    }
}
