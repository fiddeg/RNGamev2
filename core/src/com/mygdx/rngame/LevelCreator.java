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
