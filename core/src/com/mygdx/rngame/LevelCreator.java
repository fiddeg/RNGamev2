package com.mygdx.rngame;

import java.util.ArrayList;

/**
 * Created by macbookpro on 2018-03-12.
 */

public class LevelCreator {



    Character character;


    public void generateObjects(){
        generatePlayer();
    }

    public ArrayList<GameObject> generateObstacles(){
        ArrayList<GameObject> obstacles = new ArrayList<GameObject>();

        return obstacles;

    }

    private void generatePlayer(){
        character = new Character("character_idle_0.png", 0,0, 200,200);

    }

    public Character getCharacter(){
        return character;
    }



}
