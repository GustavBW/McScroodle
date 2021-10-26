package com.Testing;

import java.util.ArrayList;

public interface Interactable {
    ArrayList<Interactable> interactables = new ArrayList<>();

    void onInstancedInter();

    void Tick();
}
