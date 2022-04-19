package gbw.tdg.towerdefensegame;

public class Decimals {

    public static double toXDecimalPlaces(double d, int places){
        int multiplier = (int) Math.pow(10,places);
        double temp = d * multiplier;
        int temp2 = (int) (temp);
        return (double) temp2 / multiplier;
    }

}
