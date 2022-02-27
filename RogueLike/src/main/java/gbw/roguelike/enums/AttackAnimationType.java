package gbw.roguelike.enums;

public enum AttackAnimationType {

    LIGHT_ONE("lightOne"),
    LIGHT_TWO("lightTwo"),
    HEAVY_ONE("heavyOne"),

    STAFF_ATTACK("staffAttack"),
    UNKNOWN("youDoneGoofed");

    public String name;

    AttackAnimationType(String name){
        this.name = name;
    }
}
