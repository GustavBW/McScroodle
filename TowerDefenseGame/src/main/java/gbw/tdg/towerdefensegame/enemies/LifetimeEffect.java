package gbw.tdg.towerdefensegame.enemies;

public abstract class LifetimeEffect {

    protected long lifetimeMS,spawnTime,lastCall;

    protected void evalLifetime(Enemy toWhomAmIApplied){
        toWhomAmIApplied.removeLifetimeEffect(this);
    }

    public void evaluateOn(Enemy e){};

    public abstract String getEffectString();
}
