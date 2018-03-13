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
            obstacles.add(new Obstacle("leafy_ground01.png",0,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground02.png",150,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground01.png",300,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground02.png",450,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground01.png",600,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground02.png",600,150,150,150,false));
            obstacles.add(new Obstacle("leafy_ground01.png",750,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground02.png",750,150,150,150,false));
            obstacles.add(new Obstacle("leafy_ground01.png",750,300,150,150,false));
            obstacles.add(new Obstacle("lava_1.png",900,0,150,150,true));
            obstacles.add(new Obstacle("lava_1.png",1050,0,150,150,true));
            obstacles.add(new Obstacle("leafy_ground02.png",1200,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground01.png",1350,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground02.png",1500,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground01.png",1650,0,150,150,false));
            obstacles.add(new Obstacle("leafy_ground02.png",1800,0,150,150,false));
        }
        return obstacles;

    }

    private void generatePlayer(){
        character = new Character("character_idle_0.png", 0,150, 100,100);

    }

    public Character getCharacter(){
        return character;
    }



}
