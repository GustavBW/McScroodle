package gbw.roguelike;

import gbw.roguelike.enums.ExitDirection;
import gbw.roguelike.interfaces.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoomExit extends GameObject implements Renderable {

    private final ExitDirection direction;
    private Point2D position;
    private Point2D pointingTowards;
    private Point2D roomPos;
    private final Room roomIBelongTo;

    public RoomExit(ExitDirection e, Point2D p, Room roomIBelongTo){
        this.direction = e;
        this.position = p;
        this.roomIBelongTo = roomIBelongTo;
        this.roomPos = new Point2D(0,0);
        this.pointingTowards = calcPointingTowards();
    }

    public ExitDirection getDirection(){return direction;}

    public Point2D getPosition(){return position;}

    @Override
    public Point2D getSize() {
        return new Point2D(0,0);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRoundRect( WorldSpace.worldSpaceOffset.getX() + position.getX() - 10, WorldSpace.worldSpaceOffset.getY() + position.getY() - 10, 20, 20, 20, 20);

        gc.setFill(Color.RED);
        gc.fillRoundRect(WorldSpace.worldSpaceOffset.getX() + pointingTowards.getX() - 5, WorldSpace.worldSpaceOffset.getY() + pointingTowards.getY() - 5, 10, 10, 10, 10);
    }

    public Room getRoom(){
        return roomIBelongTo;
    }

    public void setRoomPos(Point2D p){
        this.roomPos = p;
        position = position.add(roomPos);
        pointingTowards = calcPointingTowards();
    }

    private Point2D calcPointingTowards(){
        return new Point2D( position.getX() + 50 * direction.xOffset,  position.getY() + 50 * direction.yOffset);
    }

    @Override
    public String toString(){
        return "Room Exit " + direction.direction.alias + " for room " + roomIBelongTo.getId();
    }
}
