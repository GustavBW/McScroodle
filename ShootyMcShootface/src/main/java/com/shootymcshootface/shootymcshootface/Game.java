package com.shootymcshootface.shootymcshootface;

public class Game implements Runnable{

    public static double Height = 1000, Width = 1500;
    public static boolean isRunning = false, isWaiting = false;

    public Game(){




        start();
    }



    public void play(){






    }


    public void start(){




    }


    @Override
    public void run() {
        isRunning = true;
        play();
    }
}
