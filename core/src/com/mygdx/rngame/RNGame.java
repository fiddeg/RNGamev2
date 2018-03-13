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
	private SpriteBatch batch;
	private Texture backImg;
	private LevelCreator levelCreator;
	private ArrayList<GameObject> gameObjects;

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
		gameObjects = levelCreator.generateObstacles(1);

		font = new BitmapFont();
		font.setColor(Color.FIREBRICK);


	}

	@Override
	public void render () {
		checkInput();
		for (GameObject object : gameObjects){
			if (object instanceof Obstacle){
				if (character.collidesWith(object.getBoundingRectangle()) && !((Obstacle) object).isEvil()){
					//Kollar från vilket håll karaktären kolliderar med ett block.
					if (character.getY() >= ((object.getY()+object.getHeight())-30)){ //Uppifrån
						character.setY(object.getY()+object.getHeight());
						character.setCurrentState(Character.JumpState.STATIONARY);
					} else if (((character.getX()+character.getWidth()+10) >= object.getX())
							&& (character.getX()+character.getWidth()-20) < (object.getX()+object.getWidth())){ //Karaktären kommer från höger
						character.setX(object.getX()-(character.getWidth()-10));
					} else if ((character.getX()+30) >= (object.getX()+object.getWidth())){ //Karaktären kommer från vänster.
						character.setX((object.getX()+object.getWidth())-10);
					}
				}
			}
		}
		character.updatePositionFromSpeed();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		character.draw(batch);

		for (GameObject object : gameObjects){
			object.draw(batch);
		}
		font.draw(batch, highScore, 0, Gdx.graphics.getHeight());

		batch.end();
	}



	private void checkInput(){

		//highScore=String.valueOf(character.getCurrentState());
		if (Gdx.input.justTouched()){
			character.jump();
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
		font.dispose();
		batch.dispose();
		backImg.dispose();
	}
}
