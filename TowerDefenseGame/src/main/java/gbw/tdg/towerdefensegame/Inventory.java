package gbw.tdg.towerdefensegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import gbw.tdg.towerdefensegame.UI.buttons.TowerBuyButton;

public class Inventory<T extends IGameObject> {

    protected int slotCount;
    protected List<T> objects;

    public Inventory(int slotCount){
        this.slotCount = slotCount;
        this.objects = new ArrayList<>(slotCount);
        for(int i = 0; i < slotCount; i++){
            objects.add(null);
        }
    }

    public List<T> addAll(List<T> list){
        List<T> thoseWhoSucceeded = new ArrayList<>();

        for(T obj : list){
            if(this.add(obj)){
                thoseWhoSucceeded.add(obj);
            }
        }
        return thoseWhoSucceeded;
    }

    public List<T> removeAll(List<T> list){
        List<T> thoseWhomWhereRemoved = new ArrayList<>();

        for(T obj : list){
            if(this.remove(obj)){
                thoseWhomWhereRemoved.add(obj);
            }
        }
        return thoseWhomWhereRemoved;
    }

    public boolean add(T newObject){
        for(int i = 0; i < slotCount; i++){
            if(objects.get(i) == null){
                objects.set(i,newObject);
                return true;
            }
        }
        return false;
    }

    public boolean add(T newObject, int slot){
        if(slot < slotCount && slot >= 0){
            if(objects.get(slot) == null){
                objects.set(slot,newObject);
                return true;
            }
        }
        return false;
    }
    public boolean replace(T obj1, T obj2){
        int index = getIndexOf(obj1);
        if(index != -1){
            remove(obj1);
            add(obj2,index);
        }
        return false;
    }
    public boolean remove(T obj){
        for(int i = 0; i < slotCount; i++){
            if(objects.get(i) == obj){
                objects.set(i,null);
                return true;
            }
        }
        return false;
    }
    public List<T> getAll(){
        return objects;
    }
    public T get(int slot){
        if(slot < slotCount && slot >= 0) {
            return objects.get(slot);
        }
        return null;
    }
    public int get(T obj){
        return getIndexOf(obj);
    }
    public int getIndexOf(T obj){
        for(int i = 0; i < slotCount; i++){
            if(objects.get(i) == obj){
                return i;
            }
        }
        return -1;
    }
}
