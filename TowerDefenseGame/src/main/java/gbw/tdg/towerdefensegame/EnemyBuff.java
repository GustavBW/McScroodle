package gbw.tdg.towerdefensegame;

public class EnemyBuff {

    private double bonusSpeed;
    private double health;

    public EnemyBuff(double bS, double bHP){
        this.bonusSpeed = bS;
        this.health = bHP;
    }

    public double getBonusSpeed(){return bonusSpeed;}
    public double getHealth(){return health;}

    public void increment(double health, double speed){
        this.health += health;
        this.bonusSpeed += speed;
    }
}
