package gbw.roguelike;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Minimap {

    private final static Color backgroundColor = new Color(0,0,0,0.5);
    private final static Color roomColor = new Color(1,1,1,0.5);

    private Canvas canvas;
    private final Point2D position;
    private GraphicsContext gc;
    private RoomChart roomChart;
    private final Point2D size;
    private double scaleX = 2;
    private double scaleY = 2;
    private final ArrayList<Dot> roomsAsDots;

    public Minimap(Point2D p, Point2D size) {
        this.position = p;
        this.size = size;
        this.roomsAsDots = new ArrayList<>();

        canvas = new Canvas(size.getX(), size.getY());
        canvas.setLayoutX(position.getX());
        canvas.setLayoutY(position.getY());
        canvas.setScaleX(scaleX);
        canvas.setScaleY(scaleY);
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public void drawRoomChart(RoomChart r){
        this.roomChart = r;
        scaleX = size.getX() / roomChart.getWidth();
        scaleY = size.getY() / roomChart.getHeight();

        roomsAsDots.clear();
        Room[][] currentChart = roomChart.getChart();

        for(int y = 0; y < roomChart.getHeight(); y++){
            for(int x = 0; x < roomChart.getWidth(); x++){

                if(currentChart[y][x] != null) {
                    roomsAsDots.add(new Dot(new Point2D(x * scaleX, y * scaleY), scaleX, scaleY));
                }

            }
        }
        System.out.println("roomsAsDots contains " + roomsAsDots.size() + " dots");
    }

    public void drawRoomChart(ArrayList<Room> list){
        roomsAsDots.clear();

        for(Room r : list){
            roomsAsDots.add(new Dot(
                    new Point2D(
                            r.getPosition().getX(),
                            r.getPosition().getY()),
                    2,2));
        }
    }

    public void render() {
        gc = canvas.getGraphicsContext2D();

        gc.clearRect(position.getX(), position.getY(), size.getX(),size.getY());

        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());

        gc.setFill(roomColor);
        for(Dot d : roomsAsDots){
            d.render(gc, WorldSpace.worldSpaceOffset.multiply(1.00 / roomChart.getIncrement()));
        }
    }
}
