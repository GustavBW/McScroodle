package com.testing.shadersandstuff;

public interface Texture {

    void computePixel(Pixel px);
    int getSeed();
    TextureType getType();
}
