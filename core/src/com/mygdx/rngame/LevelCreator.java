package com.mygdx.rngame;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Created by macbookpro on 2018-03-12.
 */

public class LevelCreator {
    Character character;

    public void generateObjects(){
        generatePlayer();
    }

    public ArrayList<GameObject> generateObstacles(int level){
        ArrayList<GameObject> obstacles = new ArrayList<GameObject>();
        if (level == 1){
            obstacles.add(new Obstacle("lava_1.png",768,0,128,128,true));
            obstacles.add(new Obstacle("lava_1.png",896,0,128,128,true));
            obstacles.add(new Obstacle("lava_1.png",1024,0,128,128,true));
            obstacles.add(new Obstacle("flag1.png",Gdx.graphics.getWidth()-50, 80, 50, Gdx.graphics.getHeight()/2, false));

        }
        if (level == 2){
            obstacles.add(new Obstacle("water1.png",1024,0,128,128,false));
            obstacles.add(new Obstacle("water1.png",1152,0,128,128,false));
            obstacles.add(new Obstacle("water1.png",1280,0,128,128,false));
            obstacles.add(new Obstacle("flag2.png",Gdx.graphics.getWidth()-50, 208, 50, Gdx.graphics.getHeight()/2, true));

        }
        if (level == 3){
            obstacles.add(new Obstacle("steam.png",384,0,128,128,true));
            obstacles.add(new Obstacle("steam.png",640,0,128,128,true));
            obstacles.add(new Obstacle("steam.png",768,0,128,128,true));
            obstacles.add(new Obstacle("steam.png",1408,0,128,128,true));
            obstacles.add(new Obstacle("flag1.png",Gdx.graphics.getWidth()-50, 80, 50, Gdx.graphics.getHeight()/2, false));

        }



        return obstacles;

    }

    private void generatePlayer(){
        character = new Character("character_idle_0.png", 0,130, 100,100);

    }

    public Character getCharacter(){
        return character;
    }



}
