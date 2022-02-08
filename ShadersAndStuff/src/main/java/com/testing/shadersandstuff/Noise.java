package com.testing.shadersandstuff;

import java.util.Random;

public class Noise implements Texture{

    private final NoiseType type;
    private final int seed;
    private final Random random;

    public Noise(NoiseType t,int seed){
        this.type = t;
        this.seed = seed;
        this.random = new Random(seed);
    }

    public Noise(){
        this(NoiseType.White, 1);
    }

    public void computePixel(Pixel px){
        if(type == NoiseType.White){
            px.setBW(random.nextDouble() * 255);
        }else if(type == NoiseType.Perlin){


        }

    }


    public int getSeed(){return seed;}

    @Override
    public TextureType getType() {
        return TextureType.Noise;
    }
}
