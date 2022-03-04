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
        this.size = size;
        this.position = p;
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
        roomsAsDots.clear();

        for(Vector2D pos : r.getAllRoomPositions()){
            roomsAsDots.add(new Dot(pos, scaleX, scaleY));
        }

        System.out.println("roomsAsDots contains " + roomsAsDots.size() + " dots");
    }

    public void drawRoomChart(ArrayList<Room> list){
        roomsAsDots.clear();

        for(Room r : list){
            roomsAsDots.add(new Dot(
                    new Vector2D(
                            r.getPosition().getX(),
                            r.getPosition().getY()),
                    2,
                    4)
            );
        }
    }

    public void render() {
        gc = canvas.getGraphicsContext2D();

        gc.clearRect(position.getX(), position.getY(),size.getX(),size.getY());

        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());

        gc.setFill(roomColor);
        for(Dot d : roomsAsDots){
            d.render(gc);
        }
    }


    private class Dot {

        private final Vector2D position;
        private final double sizeX;
        private final double sizeY;

        public Dot(Vector2D position, double sizeX, double sizeY) {
            this.position = position;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        public void render(GraphicsContext gc) {
            gc.fillRect(position.getX(), position.getY(),sizeX,sizeY);
        }
    }
}
