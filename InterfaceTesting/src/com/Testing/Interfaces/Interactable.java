package com.Testing.Interfaces;

import java.awt.*;
import java.util.ArrayList;

public interface Interactable {
    ArrayList<Interactable> interactables = new ArrayList<>();

    void onInstancedInter();
    void Tick();
    Point getPos();
}
