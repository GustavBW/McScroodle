package gbw.roguelike.animationSystem;

import gbw.roguelike.Weapon;
import gbw.roguelike.backend.ContentEngine;
import gbw.roguelike.enums.AttackAnimationType;
import gbw.roguelike.interfaces.Tickable;
import javafx.scene.image.Image;

import java.util.HashMap;

public class WeaponAnimator implements Tickable {

    private HashMap<AttackAnimationType, Image[]> animations;

    public WeaponAnimator(Weapon weapon){
        this.animations = ContentEngine.getWeaponAnimations(weapon.getName());
    }



    public void loadNew(){

    }

    @Override
    public void tick() {

    }
}
