package com.Testing;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class GameWindow extends Canvas {
    @Serial
    private static final long serialVersionUID = -114206942069420421L;
    private static int width, height;
    public static JFrame frame;

    public GameWindow(int width, int height, String title, Game game){
//        this.width = width;
//        this.height = height;
//
//        frame = new JFrame(title);
//
//        frame.setPreferredSize(new Dimension(width, height));
//        frame.setMaximumSize(new Dimension(width, height));
//        frame.setMinimumSize(new Dimension(width, height));
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        System.out.println("game: " + game);
//        frame.add(game);
        game.start();
    }



    public Point getScreenDimensions(){
        return new Point(width, height);
    }
}
