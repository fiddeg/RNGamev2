package com.mygdx.rngame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RNGame extends ApplicationAdapter {
	private Character character;
	SpriteBatch batch;
	Texture backImg;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		backImg = new Texture("parallax background.png");
		character = new Character("character_idle_0.png", 0,0, 150,150);
	}

	@Override
	public void render () {
		checkInput();
		character.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		character.draw(batch);
		batch.end();
	}

	private void checkInput(){
		if (Gdx.input.justTouched()){
			character.setSpeedX(3);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		backImg.dispose();
	}
}
