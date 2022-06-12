package gbw.circlerenderer;

public class SpiralingOrbit {

    private static double tooPi = Math.PI * 2;
    private int step, circumeferenceThisOrbit;
    private double[] dim, center;
    private double radiusIncrease, radiusLimit, radius;


    public SpiralingOrbit(double[] dim, double margin, double[] center){
        this.dim = dim;
        this.radiusIncrease = margin;
        this.center = center;
        this.radiusLimit = (Math.min(dim[0],dim[1]) - 2) * .5;
        this.radius = 1;
        this.circumeferenceThisOrbit = getCircumference(radius);
    }


    public double[] getNext(){
        if(step > circumeferenceThisOrbit){
            circumeferenceThisOrbit = getCircumference(radius);
            step = 0;

        }
        radius += (radiusIncrease * (1D / circumeferenceThisOrbit));
        if(radius >= radiusLimit){return null;}

        double x = Math.cos(((double) step / circumeferenceThisOrbit) * tooPi) * radius;
        double y = Math.sin(((double) step / circumeferenceThisOrbit) * tooPi) * radius;
        x += center[0];
        y += center[1];

        step++;

        return new double[]{
                x,y
        };
    }

    public double getRadiusIncrease(){
        return radiusIncrease;
    }

    private int getCircumference(double radius){
        return (int) (tooPi * radius);
    }

}
