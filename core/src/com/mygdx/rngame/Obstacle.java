package com.mygdx.rngame;

/**
 * Created by macbookpro on 2018-03-12.
 */

public class Obstacle extends GameObject {

    private boolean isEvil;

    public Obstacle(String textureFileName, float x, float y, int sizeX, int sizeY, Boolean isEvil ){
        super(textureFileName, x, y, sizeX, sizeY);
        super.setOriginCenter();
        this.isEvil = isEvil;
    }

    public boolean isEvil() {
        return isEvil;
    }
}
