package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Inventory;
import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class GraphicalInventory<T extends Renderable> extends Inventory<T> implements Renderable{

    private Color backgroundColor = new Color(0,0,0,0.5);
    private double width, height, margin, slotWidth, slotHeight, objWidth, objHeight, renderingPriority,coloumns;
    private HashMap<Integer, Point2D> slotOffsetMap;
    private HashMap<Integer, Point2D> objOffsetMap;
    private Point2D position;
    private int rows;
    private boolean enableAutoSpawn, renderBackground = true;

    public GraphicalInventory(int coloumns, int rows, Point2D dim, Point2D pos, double margin, double rendPrio){
        this(coloumns,dim.getX(),dim.getY(),margin, pos,rendPrio,rows);
    }

    public GraphicalInventory(int slotCount, double width, double height, double margin, Point2D position, double renderingPrio) {
        this(slotCount, width,height,margin,position,renderingPrio,1);
    }

    public GraphicalInventory(int coloumns, double width, double height, double margin, Point2D position, double renderingPrio, int rows) {
        super(rows * coloumns);
        this.rows = rows;
        this.coloumns = coloumns;
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
        if(renderBackground) {
            gc.setFill(backgroundColor);
            gc.fillRect(position.getX(), position.getY(), width, height);
        }
    }

    private HashMap<Integer, Point2D> calcSlotOffsets() {
        HashMap<Integer, Point2D> slotOffsets = new HashMap<>();
        double slotRawWidth = getSlotWidth();
        double slotRawHeight = height / rows;
        int currentOffsetNr = 0;

        for(int j = 0; j < rows; j++){
            for(int i = 0; i < coloumns; i++) {
                Point2D offset = new Point2D(
                        position.getX() + (i * slotRawWidth),
                        position.getY() + (j * slotRawHeight)
                );
                slotOffsets.put(currentOffsetNr, offset);
                currentOffsetNr++;
            }
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

    public List<T> setInventory(List<T> list){
        if(list == null || list.isEmpty()){
            return null;
        }
        clear();
        return this.addAll(list);
    }

    public boolean clear(){
        removeAll(getObjects());
        return super.objects.size() == 0;
    }


    private List<T> getObjects(){
        List<T> output = new ArrayList<>();
        for(T obj : super.getAll()){
            if(obj != null){
                output.add(obj);
            }
        }
        return output;
    }

    public boolean addIfAbsent(T obj){
        if(!super.objects.contains(obj)){
            this.add(obj);
            return true;
        }
        return false;
    }

    public int removeMissing(Collection<T> list){
        //Removes from this Inventory all objects that are not in the given Collection
        int count = 0;

        for(T obj : super.objects){
            if(!list.contains(obj)){
                this.remove(obj);
                count++;
            }
        }
        return count;
    }

    public List<T> addMissing(Collection<T> list){
        //Just calls this.addIfAbsent() but return those that were
        List<T> thoseWhoWhereMissing = new LinkedList<>();
        for(T obj : list){
            if(this.addIfAbsent(obj)){
                thoseWhoWhereMissing.add(obj);
            }
        }
        return thoseWhoWhereMissing;
    }

    @Override
    public boolean add(T object){
        boolean success = super.add(object);

        if(success){
            addObject(object,super.getIndexOf(object));
        }

        return success;
    }
    @Override
    public boolean add(T object, int slot){
        boolean success = super.add(object, slot);

        if(success){
            addObject(object,slot);
        }

        return success;
    }

    protected void addObject(T object, int slot){
        if(enableAutoSpawn){
            object.spawn();
        }
        object.setPosition(objOffsetMap.get(slot));
        object.setDimensions(new Point2D(objWidth,objHeight));
        object.setRenderingPriority(renderingPriority + 0.01);
    }
    @Override
    public List<T> addAll(List<T> list){
        List<T> thoseWhoSucceeded = super.addAll(list);
        for(T obj : thoseWhoSucceeded){
            addObject(obj, super.getIndexOf(obj));
        }
        return thoseWhoSucceeded;
    }
    @Override
    public boolean replace(T obj1, T obj2){
        boolean success = super.replace(obj1, obj2);

        if(success){
            this.addObject(obj2,super.getIndexOf(obj2));
        }
        return success;
    }
    @Override
    public boolean remove(T obj){
        if(obj == null){return false;}
        boolean success = super.remove(obj);
        if(success){
            obj.destroy();
        }
        return success;
    }

    public List<T> removeAll(List<T> list){
        List<T> thoseWhomWhereRemoved = super.removeAll(list);
        for(T obj : thoseWhomWhereRemoved){
            remove(obj);
        }
        return thoseWhomWhereRemoved;
    }

    @Override
    public void spawn() {
        enableAutoSpawn = true;
        Renderable.newborn.add(this);
        for(T obj : getObjects()){
            obj.spawn();
        }
    }
    @Override
    public void destroy() {
        enableAutoSpawn = false;
        Renderable.expended.add(this);
        for(T obj : getObjects()){
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
    public void setBackgroundColor(Color color){
        backgroundColor = color;
    }
    public void setRenderBackground(boolean state){
        renderBackground = state;
    }
    @Override
    public void setRenderingPriority(double newPrio) {
        this.renderingPriority = newPrio;
        for(T obj : getObjects()){
            obj.setRenderingPriority(newPrio);
        }
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
    @Override
    public Point2D getDimensions(){
        return new Point2D(width,height);
    }
    public void setAutoSpawn(boolean val){enableAutoSpawn = val;}

    private void setObjectPositions(){
        for(int i = 0; i < super.slotCount; i++){
            if(super.get(i) == null){continue;}
            super.get(i).setPosition(objOffsetMap.get(i));
        }
    }
    private void setObjectDimensions(){
        for(int i = 0; i < super.slotCount; i++){
            if(super.get(i) == null){continue;}
            super.get(i).setDimensions(new Point2D(objWidth,objHeight));
        }
    }
    private double getSlotWidth(){return width / (slotCount / rows);}
    private double getSlotHeight(){return height / rows;}
    private double getObjWidth(){
        return slotWidth - (margin * 2);
    }
    private double getObjHeight(){return slotHeight - (margin * 2);}

}
