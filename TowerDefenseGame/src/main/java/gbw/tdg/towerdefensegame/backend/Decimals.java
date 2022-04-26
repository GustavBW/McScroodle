package gbw.tdg.towerdefensegame.backend;

public class Decimals {

    public static double toXDecimalPlaces(double d, int places){
        if(places <= 0){return (int) d;}

        int multiplier = (int) Math.pow(10,places);
        double temp = d * multiplier;
        int temp2 = (int) (temp);
        return (double) temp2 / multiplier;
    }

}
