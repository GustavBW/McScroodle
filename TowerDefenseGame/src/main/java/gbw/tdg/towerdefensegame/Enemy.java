package gbw.tdg.towerdefensegame;

import gbw.tdg.towerdefensegame.UI.Clickable;
import gbw.tdg.towerdefensegame.UI.Coin;
import gbw.tdg.towerdefensegame.UI.FancyProgressBar;
import gbw.tdg.towerdefensegame.UI.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Clickable,Tickable,IEnemy{

    private final static double renderingPriority = 40D;
    private WayPoint latest;
    private WayPoint next;
    private Point2D position;
    private double mvspeed = 15, minDistToPoint = 10, sizeX = 40, sizeY = sizeX;
    private final Path path;
    private final ProgressBar hpBar;
    private int maxHP = 1, id, hp = maxHP;
    private double lengthTraveled = 0;
    private static int enemyCount = 0;
    private boolean alive = true,selected = false;
    private final Color color;

    public Enemy(Point2D position, Path path){
    this.position = position;
        this.path = path;
        this.color = new Color(Main.random.nextInt(1,255)/255.0,0,Main.random.nextInt(1,255) / 255.0,1);
        latest = path.getStartPoint();
        next = path.getNext(latest);
        hpBar = new FancyProgressBar(sizeX * 2, sizeY * 0.2,position, color,
                new Color(0 / 255.0,0/255.0,0,0.8));

        enemyCount++;
        this.id = enemyCount;
    }
    @Override
    public void tick(){
        if(hp <= 0){
            onKilled();
        }

        if(alive) {
            Point2D dir = checkDistanceToNext();

            if (dir != null) {
                dir = dir.normalize();

                dir = dir.multiply(mvspeed);

                position = position.add(dir);
                lengthTraveled += new Point2D(dir.getX(), dir.getY()).magnitude();
            }

            hpBar.setVal((double) hp / maxHP);
            hpBar.setPosition(new Point2D(position.getX() - (sizeX * 0.5),position.getY() - (sizeY * 0.5)));
        }
    }

    private void onKilled() {
        alive = false;
        for(int i = 0; i < 6; i++){
            new Coin(maxHP * 0.1, position, 100, 58).spawn();
        }
        destroy();
    }

    @Override
    public void render(GraphicsContext gc){
        if(selected){
            gc.setFill(Color.GOLD);
            gc.fillRect(position.getX()-4, position.getY() -4,sizeX+4,sizeY+4);
        }

        gc.setFill(color);
        gc.fillRect(position.getX(), position.getY(), sizeX,sizeY);

        hpBar.render(gc);
    }

    private Point2D checkDistanceToNext(){
        double distX = (next.x - position.getX()) * (next.x - position.getX());
        double distY = (next.y - position.getY()) * (next.y - position.getY());
        double distance = Math.sqrt(distX + distY);

        if(distance < minDistToPoint){
            latest = next;
            next = path.getNext(next);
        }

        if(latest == path.getEndPoint()){
            completedRun();
            return null;
        }

        return new Point2D(next.x - position.getX(), next.y - position.getY());
    }
    public void applyBuff(EnemyBuff buff){
        hp += buff.getHealth();
        maxHP += buff.getHealth();
        mvspeed += buff.getBonusSpeed();
    }
    private void completedRun(){
        Main.HP--;
        destroy();
    }

    @Override
    public void spawn() {
        Clickable.newborn.add(this);
        Tickable.newborn.add(this);
        Renderable.newborn.add(this);
        IEnemy.newborn.add(this);
    }
    @Override
    public void destroy(){
        Clickable.expended.add(this);
        Tickable.expended.add(this);
        Renderable.expended.add(this);
        IEnemy.expended.add(this);
    }
    @Override
    public boolean isInBounds(Point2D pos) {
        return (pos.getX() < position.getX() + sizeX && pos.getX() > position.getX())
                && (pos.getY() < position.getY() + sizeY && pos.getY() > position.getY());
    }
    @Override
    public void onInteraction() {
        selected = !selected;
        if(!Main.onPause) {
            hp--;
        }
        System.out.println("OOF");
    }
    @Override
    public Point2D getPosition(){return position;}
    @Override
    public double getRenderingPriority() {
        return renderingPriority;
    }
    @Override
    public void setPosition(Point2D p) {
        this.position = p;
    }
    @Override
    public void setDimensions(Point2D dim) {
        sizeX = dim.getX();
        sizeY = dim.getY();
    }
    @Override
    public double getProgress(){
        return lengthTraveled / path.getPathLength();
    }
    @Override
    public int getHp(){return hp;}
    @Override
    public void changeHp(double amount){hp += amount;}
    @Override
    public double getMvspeed(){
        return mvspeed;
    }
    @Override
    public double getSize(){return sizeX;}
    @Override
    public void deselect(){
        selected = false;
    }
    @Override
    public String toString(){return "Enemy " + id;}
}
