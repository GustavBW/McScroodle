package com.testing.shadersandstuff;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RenderableText implements Renderable{

    private Point2D position;
    private String text;
    private Font font;

    public RenderableText(Point2D pos, String text){
        this.text = text;
        this.position = pos;
        font = Font.font( "Impact", 13);
    }

    @Override
    public void render(GraphicsContext gc) {

        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.fillText(text,position.getX() +2,position.getY() +2);

        gc.setFill(Color.WHITE);
        gc.fillText(text,position.getX(),position.getY());
    }


    public void setText(String s){text = s;}
    public String getText(){return text;}
}
