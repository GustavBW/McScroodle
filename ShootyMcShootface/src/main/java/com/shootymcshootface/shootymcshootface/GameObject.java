package com.shootymcshootface.shootymcshootface;

import java.util.ArrayList;

public abstract class GameObject {

    private double x,y;
    private long id;
    private static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<GameObject> playerObjects = new ArrayList<>();
    public static ArrayList<GameObject> enemyObjects = new ArrayList<>();
    public static ArrayList<GameObject> colliderObjects = new ArrayList<>();
    public static ArrayList<GameObject> passiveObjects = new ArrayList<>();


    public GameObject(double x, double y, GameObjectType type){
        this.x = x;
        this.y = y;
        generateID();
        gameObjects.add(this);

        switch (type){
            case Enemy -> enemyObjects.add(this);
            case Player -> playerObjects.add(this);
            case Collider -> colliderObjects.add(this);
            case Passive -> passiveObjects.add(this);
        }
    }
    public GameObject(double x, double y, long id, GameObjectType type){
        this(x,y,type);
        this.id = id;
    }



    private void generateID(){
        id = gameObjects.size() + 1;
    }

    public double x(){return x;}
    public double y(){return y;}
    public void setX(double i){x = i;}
    public void setY(double i){y = i;}
    public double changeX(double i){x += i; return x;}
    public double changeY(double i){y += i; return y;}
    public long getId(){return id;}

    public static ArrayList<GameObject> getGameObjects(){return gameObjects;}
    public static GameObject getGameObject(GameObject go){
        GameObject toReturn = null;
        for(GameObject g : gameObjects){
            if(g == go){
                toReturn = g;
                break;
            }
        }
        return toReturn;
    }
    public static GameObject getGameObject(long id){
        GameObject toReturn = null;
        for(GameObject go : gameObjects){
            if(go.getId() == id){
                toReturn = go;
                break;
            }
        }
        return toReturn;
    }

}
