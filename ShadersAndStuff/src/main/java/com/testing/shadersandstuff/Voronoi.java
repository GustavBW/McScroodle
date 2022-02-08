package com.testing.shadersandstuff;

import java.util.Random;

public class Voronoi implements Texture{

    private Point[] points;
    private Random random;
    private final int seed;

    public Voronoi(Point[] points, int seed){
        this.points = points;
        this.seed = seed;
        random = new Random(seed);
    }

    public Voronoi(int amount, int seed){
        this.seed = seed;
        random = new Random(seed);
        points = computePoints(amount);
    }


    public void computePixel(Pixel px){
        double closestPxDist = 10000;
        double closestPyDist = 10000;
        double finalVal = 0;

        for(Point p : points){
            double xdist = (px.x - p.x) * (px.x - p.x);
            double ydist = (px.y - p.y) * (px.y - p.y);

            finalVal = (closestPxDist + closestPyDist);

            if((xdist + ydist) < finalVal) {
                closestPxDist = xdist;
                closestPyDist = ydist;
            }
        }

        px.setColor((Math.sqrt(finalVal) * 1.7),0,0);
    }

    private Point[] computePoints(int amount){

        Point[] points = new Point[amount];

            for(int i = 0; i < amount; i++){
            points[i] = new Point(random.nextInt(0,(int) Main.canvasDim.getX()), random.nextInt(0,(int) Main.canvasDim.getY()));
            System.out.println("VPoint made at: " + points[i].x + " | " + points[i].y);
        }

        return points;
    }



    public int getSeed(){return seed;}

    @Override
    public TextureType getType() {
        return TextureType.Voronoi;
    }
}
