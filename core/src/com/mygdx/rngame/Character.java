package com.mygdx.rngame;

/**
 * Created by Fidde on 2018-02-17.
 */

public class Character extends Figure{
    public Character(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);
        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed(float delta){
        super.updatePositionFromSpeed();
        stopAtEdge();
    }
}
