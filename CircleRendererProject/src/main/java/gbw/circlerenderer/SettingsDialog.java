package gbw.circlerenderer;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsDialog {

    private final Slider radiusIncreaseSlider,maxLineWidthSlider;
    private final ColorPicker backgroundColorPicker, lineColorPicker;
    private final CheckBox autoUpdateToggle;
    private Stage stage;
    private final Gui host;

    public SettingsDialog(Gui host){
        radiusIncreaseSlider = new Slider(1,100,20);
        maxLineWidthSlider = new Slider(0,1,.5);
        backgroundColorPicker = new ColorPicker(Color.WHITE);
        lineColorPicker = new ColorPicker(Color.BLACK);
        autoUpdateToggle = new CheckBox("Auto-Update");
        autoUpdateToggle.setSelected(true);
        this.host = host;
    }

    public Stage loadSetup() {
        if(stage == null) {
            VBox vbox = new VBox();
            Text t1 = new Text("Radius Increase");
            Text t12 = new Text("" + radiusIncreaseSlider.getValue());
            radiusIncreaseSlider.setOnMouseDragged(e -> {
                    t12.setText("" + radiusIncreaseSlider.getValue());
                    requestRerender();
                }
            );

            Text t2 = new Text("Max Line Width");
            Text t22 = new Text("" + maxLineWidthSlider.getValue());
            maxLineWidthSlider.setOnMouseDragged(e -> {
                    t22.setText("" + maxLineWidthSlider.getValue());
                    requestRerender();
                }
            );


            Text t3 = new Text("Background Color");
            Text t4 = new Text("Line Color");

            vbox.getChildren().addAll(
                    t1,radiusIncreaseSlider,t12,
                    t2,maxLineWidthSlider,t22,
                    t3, backgroundColorPicker,
                    t4, lineColorPicker,
                    autoUpdateToggle
            );

            stage = new Stage();
            stage.setScene(new Scene(vbox, Gui.canvasDim[0] * .3, Gui.canvasDim[1] * .3));
            stage.setTitle("Settings");
        }

        return stage;
    }

    private void requestRerender() {
        if(autoUpdateToggle.isSelected()){
            host.tryStartRender();
        }
    }


    public void open(){
        loadSetup().show();
    }



    public double getRadiusIncrease() {
        return radiusIncreaseSlider.getValue();
    }

    public double getMaxLineWidth() {
        return maxLineWidthSlider.getValue();
    }

    public Color getBackgroundColor() {
        return backgroundColorPicker.getValue();
    }

    public Color getLineColor() {
        return lineColorPicker.getValue();
    }

}
