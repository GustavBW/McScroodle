package gbw.tdg.towerdefensegame.enemies;

import gbw.tdg.towerdefensegame.EnemyBuff;
import gbw.tdg.towerdefensegame.Path;
import javafx.geometry.Point2D;

public class WobblerEnemy extends Enemy{

    public WobblerEnemy(Point2D position, Path path) {
        super(position, path);
    }

    @Override
    public void tick(){
        super.tick();

    }

    @Override
    public void applyBuff(EnemyBuff buff){
        super.applyBuff(buff);
        //increase range.. dmg... whatever
    }
}
