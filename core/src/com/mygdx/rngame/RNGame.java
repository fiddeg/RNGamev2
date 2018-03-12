package com.mygdx.rngame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class RNGame extends ApplicationAdapter {
	private double tiltTrigger = 0.65;
	private Character character;
	SpriteBatch batch;
	Texture backImg;
	LevelCreator levelCreator;
	ArrayList<GameObject> gameObjects;

	//Använde dessa till testing, händigare än logcat IMHO.
	private BitmapFont font;
	String highScore = "Highscore is: "+"9001";


	@Override
	public void create () {
		batch = new SpriteBatch();
		backImg = new Texture("parallax background.png");
		levelCreator= new LevelCreator();
		levelCreator.generateObjects();
		character = levelCreator.getCharacter();

		font = new BitmapFont();
		font.setColor(Color.FIREBRICK);


	}

	@Override
	public void render () {
		checkInput();

		character.updatePositionFromSpeed();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		character.draw(batch);
		font.draw(batch, highScore, 0, Gdx.graphics.getHeight());

		batch.end();
	}



	private void checkInput(){

		if (Gdx.input.justTouched()){
			//magic happens?

		}

		//Kollar sensorn och avgör hastigheten i X-led därefter. tiltTrigger används som gränsvärde för att kunna stå stilla.
		if (Gdx.input.getAccelerometerY() < -tiltTrigger || Gdx.input.getAccelerometerY() > tiltTrigger){
				character.setSpeedX((float) ((Gdx.input.getAccelerometerY())*2.5));
		} else {
			character.setSpeedX(0);
		}



	}

	@Override
	public void dispose () {
		batch.dispose();
		backImg.dispose();
	}
}
