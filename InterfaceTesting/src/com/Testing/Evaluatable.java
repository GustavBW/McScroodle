package com.Testing;

import java.util.ArrayList;

public interface Evaluatable {
    ArrayList<Evaluatable> evaluatables = new ArrayList<>();

    void onInstancedEval();
    void Tick();
}
