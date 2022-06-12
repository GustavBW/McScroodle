package gbw.circlerenderer;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Renderer {

    private SpiralingOrbit orbit;
    private WritableImage sampleImage;
    private WritableImage render;
    private PixelReader reader;
    private double maxLineWidth;
    private Color backgroundColor, lineColor;


    public void init(Canvas sampleCanvas, SettingsDialog settings){
        this.sampleImage = sampleCanvas.snapshot(new SnapshotParameters(),null);
        this.orbit = new SpiralingOrbit(
                new double[]{
                        sampleImage.getWidth(),
                        sampleImage.getWidth()},
                settings.getRadiusIncrease(),
                new double[]{
                        sampleImage.getWidth() *.5,
                        sampleImage.getHeight() *.5
                }
        );

        this.maxLineWidth = settings.getMaxLineWidth();
        this.backgroundColor = settings.getBackgroundColor();
        this.lineColor = settings.getLineColor();
        this.reader = sampleImage.getPixelReader();
    }

    public void run() {
        GraphicsContext gc = new Canvas(sampleImage.getWidth(),sampleImage.getHeight()).getGraphicsContext2D();
        gc.setFill(backgroundColor);
        gc.fillRect(0,0,sampleImage.getWidth(),sampleImage.getHeight());
        gc.setFill(lineColor);

        double[] currentPos = null;
        while((currentPos = orbit.getNext()) != null) {
            Color colorSample = reader.getColor((int) currentPos[0], (int) currentPos[1]);
            int pointSize = (int) (mapToPointSize(colorSample) * (orbit.getRadiusIncrease()) * maxLineWidth);
            pointSize = pointSize <= 0 ? 1 : pointSize;
            currentPos = rePosition(currentPos, pointSize);
            gc.fillRect(currentPos[0], currentPos[1], pointSize, pointSize);
        }

        render = gc.getCanvas().snapshot(new SnapshotParameters(),null);
    }

    private double[] rePosition(double[] currentPos, int pointSize) {
        return new double[]{
                currentPos[0] - (pointSize * .5),
                currentPos[1] - (pointSize * .5)
        };
    }

    private double mapToPointSize(Color colorSample) {
        double sum = colorSample.getRed() + colorSample.getBlue() + colorSample.getGreen();
        return 1 - (sum / 3);
    }

    public Image get() {
        return render;
    }
}
