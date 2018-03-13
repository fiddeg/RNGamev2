package com.mygdx.rngame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by macbookpro on 2018-03-12.
 */

public abstract class GameObject extends Sprite {


    public GameObject(String texture, float srcX, float srcY, float srcWidth, float srcHeight) {
        super.setTexture(new Texture(texture));
        super.setX(srcX);
        super.setY(srcY);
        super.setSize(srcWidth, srcHeight);
    }

    public void draw(SpriteBatch batch){
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }
}
