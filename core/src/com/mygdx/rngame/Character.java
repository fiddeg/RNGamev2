package com.mygdx.rngame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Fidde on 2018-02-17.
 */

public class Character extends Figure{
    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 2;

    //Hastigheten för animationen
    private static float FRAME_DURATION_RUNNING = 0.1f;
    private static float FRAME_DURATION_IDLE = 0.15f;

    private TextureRegion[] animationIdleFrames;
    private Animation animationIdle;
    private Animation animationRunningRight;
    private Animation animationRunningLeft;
    private Texture animationRunImg;
    float stateTime;

    public Character(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);
        super.setOriginCenter();

        //Sätter de olika bilderna för animeringen för idle läget.
        animationIdleFrames = new TextureRegion[4];
        for (int i = 0;i<4;i++){
            animationIdleFrames[i] = new TextureRegion(new Texture("character_idle_"+i+".png"));
        }

        //Sätter de olika bilderna för animeringen för running läget.
        animationRunImg = new Texture("character_run.png");
        TextureRegion[][] animationRunningFrames = TextureRegion.split(animationRunImg,
                animationRunImg.getWidth()/FRAME_COLS,
                animationRunImg.getHeight()/FRAME_ROWS);

        //Assignar rätt frames med sin animations-variabel.
        animationRunningRight = new Animation(FRAME_DURATION_RUNNING, animationRunningFrames[0]);
        animationRunningRight.setPlayMode(PlayMode.LOOP);
        animationRunningLeft = new Animation(FRAME_DURATION_RUNNING, animationRunningFrames[1]);
        animationRunningLeft.setPlayMode(PlayMode.LOOP);
        animationIdle = new Animation(FRAME_DURATION_IDLE, animationIdleFrames);
        animationIdle.setPlayMode(PlayMode.LOOP);

        //counts to keep track of key frames of the animation
        stateTime = 0;
    }




    //Metod för att hämta nuvarande frame i animationen beroende på i vilken rörelse figuren är i.
    private TextureRegion getImage(){
        stateTime += Gdx.graphics.getDeltaTime();

        if (getSpeedX() > 0){
            return (TextureRegion) animationRunningRight.getKeyFrame(stateTime);
        } else if (getSpeedX() < 0){
            return (TextureRegion) animationRunningLeft.getKeyFrame(stateTime);
        } else {
            return (TextureRegion) animationIdle.getKeyFrame(stateTime);
        }
    }

    //Metod för att override draw metoden från figure-klassen så den kan hämta animationFrame från getImage().
    public void draw(SpriteBatch batch){
        batch.draw(getImage(), getX(), getY(), getWidth(), getHeight());
    }
}
