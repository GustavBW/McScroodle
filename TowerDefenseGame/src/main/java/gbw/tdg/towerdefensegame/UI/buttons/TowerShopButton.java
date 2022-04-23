package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.TowerShop;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class TowerShopButton extends Button{

    private boolean toggle;
    private final TowerShop shop;

    public TowerShopButton(Point2D position, double sizeX, double sizeY, RText textUnit) {
        super(position, sizeX, sizeY, textUnit,true);
        shop = new TowerShop();
    }

    @Override
    public void onClick(MouseEvent event){
        toggle = !toggle;
        if(toggle){
            shop.spawn();
        }else{
            shop.destroy();
        }
    }

    @Override
    public void spawn(){
        super.spawn();
    }
    @Override
    public void destroy(){
        super.destroy();
        shop.destroy();
    }
}
