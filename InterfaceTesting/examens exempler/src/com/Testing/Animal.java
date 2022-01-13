package com.Testing;

public class Animal {

    private final String type;
    private final int age;

    public Animal(String type, int age) {
        this.type = type;
        this.age = age;
    }

    public int getAge(){return age;}

    public String getType(){return type;}

}
