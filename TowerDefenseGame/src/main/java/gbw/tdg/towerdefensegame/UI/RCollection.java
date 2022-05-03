package gbw.tdg.towerdefensegame.UI;

import gbw.tdg.towerdefensegame.Renderable;

import java.util.ArrayList;
import java.util.List;

public class RCollection {

    private List<Renderable> collection = new ArrayList<>();

    public RCollection(List<Renderable> list){
        this.collection = list;
    }
    public RCollection(){}


    public void is(List<Renderable> list){
        this.collection = list;
    }

    public void spawn(){
        for(Renderable r : collection){
            r.spawn();
        }
    }
    public void destroy(){
        for(Renderable r : collection){
            r.destroy();
        }
    }
}
