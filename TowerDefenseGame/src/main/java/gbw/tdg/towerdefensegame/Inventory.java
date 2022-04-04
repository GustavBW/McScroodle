package gbw.tdg.towerdefensegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import gbw.tdg.towerdefensegame.UI.buttons.TowerBuyButton;

public class Inventory<T extends IGameObject> {

    protected int slotCount;
    protected List<T> objects;
    protected final HashMap<Integer, T> slotObjMap;
    protected final HashMap<T, Integer> objSlotMap;


    public Inventory(int slotCount){
        this.slotCount = slotCount;
        this.slotObjMap = new HashMap<>();
        this.objSlotMap = new HashMap<>();
        this.objects = new LinkedList<T>();
    }

    public boolean addAll(List<T> list){
        boolean success = true;
        for(T obj : list){
            if(!add(obj)){
                success = false;
            }
        }
        return success;
    }
    public boolean add(T newObject){
        if(objects.size() >= slotCount){
            return false;
        }

        objects.add(newObject);
        slotObjMap.put(getIndexOf(newObject), newObject);
        objSlotMap.put(newObject,getIndexOf(newObject));
        return true;
    }

    public boolean add(T newObject, int slot){
        if(slot > slotCount || slot < 0 || objects.get(slot) != null){
            return false;
        }
        objects.set(slot, newObject);
        objSlotMap.put(newObject, slot);
        slotObjMap.put(slot,newObject);
        return true;
    }
    public boolean replace(T obj1, T obj2){
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i) == obj1){
                remove(obj1);
                add(obj2, i);
                return true;
            }
        }
        return false;
    }
    public boolean remove(T obj){
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i) == obj){
                objSlotMap.put(null,i);
                slotObjMap.put(i, null);
                objects.set(i, null);;
                obj.destroy();
                return true;
            }
        }
        return false;
    }
    public List<T> getAll(){
        return new LinkedList<T>(objects);
    }
    public List<T> getAllRaw(){
        return objects;
    }

    protected int getIndexOf(T obj){
        int size = objects.size();

        for(int i = size - 1; i >= 0; i--){
            if(objects.get(i) == obj){
                return i;
            }
        }
        return size;
    }
}
