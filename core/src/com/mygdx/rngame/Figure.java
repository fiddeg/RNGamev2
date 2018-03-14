package com.mygdx.rngame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Fidde on 2018-02-17.
 */

public class Figure extends GameObject{
    private float speedX = 0;
    private float speedY = 0;
    private final int SHRINK_COLLISION_RADIUS_X;
    private final int SHRINK_COLLISION_RADIUS_Y;

    public Figure(String textureFileName, float x, float y, int sizeX, int sizeY) {
        super(textureFileName, x, y, sizeX, sizeY);
        SHRINK_COLLISION_RADIUS_X = sizeX / 4;
        SHRINK_COLLISION_RADIUS_Y = sizeY / 8;

    }


    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float xSpeed) {
        this.speedX = xSpeed;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float ySpeed) {
        this.speedY = ySpeed;
    }





    public void stopAtEdge() {
        if (getX() > Gdx.graphics.getWidth() - getWidth())
            setX(Gdx.graphics.getWidth() - getWidth());
        if (getX() < 0)
            setX(0);
        if (getY() > Gdx.graphics.getHeight() - getHeight())
            setY(Gdx.graphics.getHeight() - getHeight());
        if (getY() < 0)
            setY(0);
    }


    public Rectangle getCollisionRectangle() {
        return new Rectangle(
                super.getX() + SHRINK_COLLISION_RADIUS_X,
                super.getY() + SHRINK_COLLISION_RADIUS_Y,
                super.getWidth() - (2 * SHRINK_COLLISION_RADIUS_X),
                super.getHeight() - (2 * SHRINK_COLLISION_RADIUS_Y));
    }

    public boolean collidesWith(Rectangle otherRect) {
        return getCollisionRectangle().overlaps(otherRect);
    }
}
