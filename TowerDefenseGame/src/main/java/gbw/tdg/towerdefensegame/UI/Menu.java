package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.IGameObject;
import gbw.tdg.towerdefensegame.Renderable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class Menu<T extends Renderable> implements Renderable{

    private List<T> elements;
    private double rendPrio;
    private Point2D position, dim;
    private int coloumns, rows;
    private Map<Integer, Point2D> positionMap;
    private boolean isSpawned = false;

    public Menu(Point2D pos, Point2D dim, double rendPrio, int coloumns, int rows){
        this.elements = new LinkedList<>();
        this.position = pos;
        this.rendPrio = rendPrio;
        this.dim = dim;
        this.coloumns = coloumns;
        this.rows = rows;
        this.positionMap = calcPositions();
    }

    private void setChildrenPos(){
        positionMap = calcPositions();

        for(int i = 0; i < elements.size(); i++){
            elements.get(i).setPosition(positionMap.getOrDefault(i,Point2D.ZERO));
        }
    }
    private Map<Integer, Point2D> calcPositions(){
        Map<Integer, Point2D> toReturn = new HashMap<>();
        double singleElWidth = dim.getX() / coloumns;
        double singleElHeight = dim.getY() / rows;

        for(int x = 0; x < coloumns; x++){
            for(int y = 0; y < rows; y++){
                toReturn.put(x+y,new Point2D(
                        position.getX() + (x * singleElWidth),
                        position.getY() + (y * singleElHeight)
                ));
            }
        }

        return toReturn;
    }
    private void setChildrenSize(){
        double singleElWidth = dim.getX() / coloumns;
        double singleElHeight = dim.getY() / rows;

        for(T obj : elements){
            obj.setDimensions(new Point2D(singleElWidth,singleElHeight));
        }
    }
    private void setChildrenRendPrio(){
        for(T obj : elements){
            obj.setRenderingPriority(rendPrio);
        }
    }

    public List<T> getChildren(){
        return new ArrayList<>(elements);
    }
    public boolean add(T obj){
        boolean success = elements.add(obj);
        if(success) {
            setChildrenPos();
            setChildrenSize();
            setChildrenRendPrio();
            if(isSpawned) {
                obj.spawn();
            }
        }
        return success;
    }
    public boolean remove(T obj){
        boolean success = elements.remove(obj);
        if(success) {
            setChildrenPos();
            setChildrenSize();
            setChildrenRendPrio();
            obj.destroy();

        }
        return success;
    }
    public boolean addAll(List<T> list){
        List<T> thoseWhomSucceeded = new LinkedList<>();
        for(T obj : list){
            if(this.add(obj)){
                thoseWhomSucceeded.add(obj);
            }
        }

        return !thoseWhomSucceeded.isEmpty();
    }
    public boolean removeAll(List<T> list){
        List<T> thoseWhomSucceeded = new LinkedList<>();

        for(T obj : list){
            if(this.remove(obj)){
                thoseWhomSucceeded.add(obj);
            }
        }

        return !thoseWhomSucceeded.isEmpty();
    }

    private void spawnAll(List<T> list){
        for(T obj : list){
            obj.spawn();
        }
    }
    private void spawnAll(){
        spawnAll(elements);
    }
    private void destroyAll(List<T> list){
        for(T obj : list){
            obj.destroy();
        }
    }
    private void destroyAll(){
        destroyAll(elements);
    }

    public void clear(){
        removeAll(elements);
        elements.clear();
    }

    @Override
    public void spawn() {
        Renderable.newborn.add(this);
        spawnAll();
        isSpawned = true;
    }

    @Override
    public void destroy() {
        Renderable.expended.add(this);
        destroyAll();
        isSpawned = false;
    }

    @Override
    public void render(GraphicsContext gc) {
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public double getRenderingPriority() {
        return rendPrio;
    }

    @Override
    public void setPosition(Point2D p) {
        this.position = p;
        setChildrenPos();
    }

    @Override
    public void setRenderingPriority(double newPrio) {
        this.rendPrio = newPrio;
    }

    @Override
    public void setDimensions(Point2D dim) {
        this.dim = dim;
        setChildrenSize();
    }
}
