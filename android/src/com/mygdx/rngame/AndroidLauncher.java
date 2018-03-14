package com.mygdx.rngame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new RNGame(new AndroidHighScore()), config);
	}

	public class AndroidHighScore implements DatabaseConnection {
		private FeedReaderAddToDatabase addToDatabase = new FeedReaderAddToDatabase(getApplicationContext());

		@Override
		public String getScore() {
			String highScore = addToDatabase.readHighScore();
			return highScore;
		}

		@Override
		public void setScore(String score) {
			addToDatabase.writeNew(score);
		}
	}
}
