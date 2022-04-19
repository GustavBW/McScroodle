package gbw.tdg.towerdefensegame.UI.buttons;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.ARText;
import gbw.tdg.towerdefensegame.UI.AugmentShop;
import gbw.tdg.towerdefensegame.UI.GraphicalInventory;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.Screens.InGameScreen;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;

public class AugmentShopButton extends Button{

    private boolean toggle;
    private final AugmentShop shop;

    public AugmentShopButton(Point2D position, double sizeX, double sizeY) {
        super(position, sizeX, sizeY, new RText(
                "Augments", position, 3, InGameScreen.goldColor, Font.font("Impact", Main.canvasSize.getX() * 0.0182)
        ), true);
        shop = new AugmentShop();
    }

    @Override
    public void onInteraction(){
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
