package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Inventory;
import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphicalInventory<T extends Renderable> extends Inventory<T> implements Renderable{

    private Color backgroundColor = new Color(0,0,0,0.5);
    private double width, height, margin, slotWidth, slotHeight, objWidth, objHeight, renderingPriority;
    private HashMap<Integer, Point2D> slotOffsetMap;
    private HashMap<Integer, Point2D> objOffsetMap;
    private Point2D position;

    public GraphicalInventory(int slotCount, double width, double height, double margin, Point2D position, double renderingPrio) {
        super(slotCount);
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.position = position;
        this.slotWidth =  getSlotWidth();
        this.slotHeight = getSlotHeight();
        this.objWidth = getObjWidth();
        this.objHeight = getObjHeight();
        this.renderingPriority = renderingPrio;
        this.slotOffsetMap = calcSlotOffsets();
        this.objOffsetMap = calcObjOffsets();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(position.getX(),position.getY(),width,height);

        for(T obj : super.objects){
            obj.render(gc);
        }
    }
    public void setBackgroundColor(Color color){
        backgroundColor = color;
    }
    @Override
    public boolean add(T object){
        boolean success = super.add(object);

        if(success){
            int invSlot = objSlotMap.get(object);
            object.setPosition(objOffsetMap.get(invSlot));
        }

        return success;
    }
    @Override
    public boolean add(T object, int slot){
        boolean success = super.add(object, slot);

        if(success){
            object.setPosition(objOffsetMap.get(slot));
        }

        return success;
    }
    @Override
    public boolean addAll(List<T> list){
        int amountWhoSucceeded = 0;

        for(T obj : list){
            if(super.add(obj)){
                obj.setPosition(objOffsetMap.get(objSlotMap.get(obj)));
                amountWhoSucceeded++;
            }
        }

        return amountWhoSucceeded + 1 == list.size();
    }
    @Override
    public boolean replace(T obj1, T obj2){
        boolean success = super.replace(obj1, obj2);
        if(success){
            this.add(obj2);
        }
        return success;
    }
    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        for(T obj : super.objects){
            obj.spawn();
        }
    }
    @Override
    public void destroy() {
        Renderable.expended.add(this);
        for(T obj : super.objects){
            obj.destroy();
        }
    }
    @Override
    public Point2D getPosition() {
        return position;
    }
    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setPosition(Point2D p) {
        this.position = p;
        this.slotOffsetMap = calcSlotOffsets();
        this.objOffsetMap = calcObjOffsets();

        setObjectPositions();
    }
    @Override
    public void setDimensions(Point2D dim) {
        width = dim.getX();
        height = dim.getY();
        slotOffsetMap = calcSlotOffsets();
        objOffsetMap = calcObjOffsets();
        slotWidth = getSlotWidth();
        slotHeight = getSlotHeight();
        objWidth = getObjWidth();
        objHeight = getObjHeight();

        setObjectPositions();
        setObjectDimensions();
    }

    private void setObjectPositions(){
        for(int i = 0; i < super.objects.size(); i++){
            super.objects.get(i).setPosition(objOffsetMap.get(i));
        }
    }
    private void setObjectDimensions(){
        for(int i = 0; i < super.objects.size(); i++){
            super.objects.get(i).setDimensions(new Point2D(objWidth,objHeight));
        }
    }
    private double getSlotWidth(){return width / slotCount;}
    private double getSlotHeight(){return height;}
    private double getObjWidth(){
        return slotWidth - (margin * 2);
    }
    private double getObjHeight(){return slotHeight - (margin * 2);}
    private HashMap<Integer, Point2D> calcSlotOffsets() {
        HashMap<Integer, Point2D> slotOffsets = new HashMap<>();
        double slotRawWidth = width / slotCount;

        for(int i = 0; i < slotCount; i++){
            Point2D offset = new Point2D(
                    position.getX() + (i * slotRawWidth),
                    position.getY()
            );
            slotOffsets.put(i,offset);
        }

        return slotOffsets;
    }
    private HashMap<Integer, Point2D> calcObjOffsets(){
        HashMap<Integer, Point2D> objOffsets = new HashMap<>();

        for(int i = 0; i < slotCount; i++){
            Point2D slotOffset = slotOffsetMap.get(i);
            objOffsets.put(i, new Point2D(
                    slotOffset.getX() + margin,
                    slotOffset.getY() + margin
            ));
        }

        return objOffsets;
    }
}
