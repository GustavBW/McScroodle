package com.Testing;

import java.awt.*;
import java.util.ArrayList;

public interface Renderable {
    ArrayList<Renderable> renderables = new ArrayList<>();

    void onInstancedRend();
    void Render(Graphics g);
}
