package com.testing.shadersandstuff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pixel implements Renderable{

    private double r,b,g;
    public final int x,y;
    private Color color;

    public Pixel (int r, int g, int b,int x, int y){
        this.r = r;
        this.b = b;
        this.g = g;
        this.x = x;
        this.y = y;

        setColor(r,g,b,1);
    }

    public Pixel(){this(255,255,255, 0,0);}
    public Pixel(int x, int y){this(255, 255, 255, x, y);}

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x,y,1,1);
    }

    public void setBW(double i){setColor(i,i,i,1);}
    public void setColor(double r, double g, double b){
        setColor(r,g,b,1);
    }
    public void setColor(double r, double g, double b, double a){
        checkValue("opacity",a);
        color = new Color((Math.abs(r)) / 255d,(Math.abs(g)) / 255d,(Math.abs(b)) / 255d, a);
    }


    public Color getColor(){return color;}
    public void setAlpha(double a){
        checkValue("opacity",a);
        color = new Color(color.getRed(), color.getGreen(),color.getBlue(), a);
    }
    public void invertColor(){
        color = new Color(1 - color.getRed(), 1- color.getGreen(),1- color.getBlue(), 1);
    }
    private void checkValue(String s, double a){
        if(a < 0 || a > 1){
            System.out.println("Illegal Pixel " + s + " value: " + a);

            if (a < 0.5){
                a = 0;
            }else{
                a = 1;
            }
        }
    }
}
