package gbw.circlerenderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Application {

    public static double[] canvasDim = new double[]{900,900};
    public static double[] sceneDim = new double[]{canvasDim[0],canvasDim[1]+100};
    private Canvas previewCanvas,renderCanvas;
    private RessourceHandler resHandler;
    private Renderer renderer;
    private SettingsDialog settings;

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        VBox vbox = new VBox();
        HBox buttonBox = new HBox();
        addButtons(buttonBox);
        vbox.getChildren().addAll(buttonBox,pane);
        previewCanvas = new Canvas(canvasDim[0],canvasDim[1]);
        renderCanvas = new Canvas(canvasDim[0],canvasDim[1]);
        pane.getChildren().addAll(previewCanvas,renderCanvas);
        prepareCanvass(previewCanvas,renderCanvas);

        resHandler = new RessourceHandler();
        renderer = new Renderer();
        settings = new SettingsDialog(this);

        Scene scene = new Scene(vbox, sceneDim[0],sceneDim[1]);
        stage.setTitle("CircleRenderer");
        stage.setScene(scene);
        stage.show();
    }

    private void prepareCanvass(Canvas previewCanvas, Canvas renderCanvas) {
        previewCanvas.getGraphicsContext2D().clearRect(0,0,canvasDim[0],canvasDim[1]);
        renderCanvas.getGraphicsContext2D().clearRect(0,0,canvasDim[0],canvasDim[1]);
    }

    private void addButtons(Pane pane){
        double width = pane.getLayoutBounds().getWidth();
        Button button1 = new Button("Load Image");
        button1.setOnMouseClicked(e -> loadNewImage());
        Button button2 = new Button("Render");
        button2.setOnMouseClicked(e -> tryStartRender());
        Button button3 = new Button("Reset");
        button3.setOnMouseClicked(e -> clearCurrentRender());
        Button button4 = new Button("Save");
        button4.setOnMouseClicked(e -> saveCurrentRender());
        Button settingsButton = new Button("Settings");
        settingsButton.setOnMouseClicked(e -> openSettingsDialog());

        List<Button> buttons = new ArrayList<>(List.of(
                settingsButton,button1,button2,button3,button4
        ));
        for(Button b : buttons){
            b.setPrefSize(sceneDim[1]-canvasDim[1],width/buttons.size());
            pane.getChildren().add(b);
        }
    }

    private void openSettingsDialog() {
        settings.open();
    }

    private void saveCurrentRender() {
        WritableImage cRender = renderCanvas.snapshot(new SnapshotParameters(),null);
        //TODO: Enable RessourceHandler.imageOut()
    }

    private void clearCurrentRender() {
        renderCanvas.getGraphicsContext2D().clearRect(0,0,canvasDim[0],canvasDim[1] );
    }

    private void loadNewImage() {
        FileChooser chooser = new FileChooser();
        File choosen = chooser.showOpenDialog(previewCanvas.getScene().getWindow());
        if(choosen == null || !resHandler.isValidImageFile(choosen.getName())){
            return;
        }
        loadPreview(choosen);
        System.out.println("YOU HAVE CHOOSEN " + choosen);
    }

    private void loadPreview(File choosen) {
        previewCanvas.getGraphicsContext2D().drawImage(
                new Image(choosen.toURI().toString()),
                0,0,canvasDim[0],canvasDim[1]
        );
    }

    public void tryStartRender() {
        if(settings == null){
            return;
        }
        startRender();
    }

    private void startRender() {
        renderer.init(previewCanvas,settings);
        renderer.run();
        Image rendered = renderer.get();
        renderCanvas.getGraphicsContext2D().drawImage(rendered, 0,0,canvasDim[0],canvasDim[1]);
    }

    public static void main(String[] args) {
        launch();
    }
}