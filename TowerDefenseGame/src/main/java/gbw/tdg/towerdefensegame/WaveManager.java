package gbw.tdg.towerdefensegame;

public class WaveManager implements Tickable{

    private long lastCall2;
    private Path path;

    public WaveManager(Path path){
        this.path = path;
    }

    @Override
    public void spawn() {
        Tickable.newborn.add(this);
    }

    @Override
    public void destroy() {
        Tickable.expended.add(this);
    }

    @Override
    public void tick() {
        if(lastCall2 + 5000 < System.currentTimeMillis()){
            new Enemy(path.getStartPoint().x,path.getStartPoint().y,path).spawn();
            lastCall2 = System.currentTimeMillis();
        }
    }
}
