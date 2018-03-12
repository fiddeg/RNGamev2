package com.mygdx.rngame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by macbookpro on 2018-03-12.
 */

public class Obstacle extends GameObject {

    private boolean isEvil;

    public Obstacle(String textureFileName, float x, float y, int sizeX, int sizeY, Boolean isEvil ){
        super(textureFileName, x, y, sizeX, sizeY);
        this.isEvil = isEvil;
    }







}
