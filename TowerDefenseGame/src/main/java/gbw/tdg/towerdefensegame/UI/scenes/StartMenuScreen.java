package gbw.tdg.towerdefensegame.UI.scenes;

import gbw.tdg.towerdefensegame.Main;
import gbw.tdg.towerdefensegame.UI.RText;
import gbw.tdg.towerdefensegame.UI.buttons.Button;
import gbw.tdg.towerdefensegame.backend.TextFormatter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartMenuScreen extends GScene {

    private final Button startGameButton;
    private final RText titleText;
    private final Font titleFont = Font.font("Impact", Main.canvasSize.getX() * 0.06);

    public StartMenuScreen(){
        super(75);
        Point2D sGBPos = new Point2D(Main.canvasSize.getX() * 0.5,Main.canvasSize.getY() * 0.65);
        RText sGBText = new RText("PLAY", Point2D.ZERO,5, Color.WHITE, Font.font("Impact", Main.canvasSize.getX() * 0.05));


        double titleWidth = TextFormatter.getWidthOf("Σ TOWER DEFENCE", titleFont);
        Point2D titlePos = new Point2D((Main.canvasSize.getX() - titleWidth) * .6, Main.canvasSize.getY() * 0.15);
        titleText = new RText("Σ TOWER DEFENCE", titlePos, 20, Color.WHITE, titleFont);

        this.startGameButton = new Button(sGBPos, Main.canvasSize.getX() * 0.2,Main.canvasSize.getY() * 0.1,sGBText,true){
            @Override
            public void onClick(MouseEvent event){
                Main.onGameStart();
            }
        };
        startGameButton.setPosition(sGBPos.subtract(startGameButton.getDimensions().multiply(0.5)));
        startGameButton.setTextAlignments(0.5,0);
    }


    public void render(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,Main.canvasSize.getX(),Main.canvasSize.getY());

        titleText.render(gc);
        startGameButton.render(gc);
    }

    @Override
    public void spawn(){
        super.spawn();
        startGameButton.spawn();
    }

    @Override
    public void destroy() {
        startGameButton.destroy();
        super.destroy();
    }

}
