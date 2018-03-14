package com.mygdx.rngame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;


public class RNGame extends ApplicationAdapter {
    private double tiltTrigger = 0.65;
    private Character character;
    private SpriteBatch batch;
    private Texture backImg;
    private LevelCreator levelCreator;
    private ArrayList<GameObject> gameObjects;
    private Texture titleScreen;
    private Music backMusic;
    private GameState gameState = GameState.TITLE_SCREEN;
    private Level1Map level1;
    private boolean isOnGround;

    //Använde dessa till testing, händigare än logcat IMHO.
    private BitmapFont highScoreFont;
    private BitmapFont timeLeftFont;
    String highScore = "Highscore is: ";
    String timeLeft = "Time left: ";
    int score = 0;
    float time = 6;

    //Meny-test
    private enum GameState {
        TITLE_SCREEN,
        LEVEL1_SCREEN,
        LEVEL2_SCREEN,
        LEVEL3_SCREEN,
        GAMEOVER_SCREEN
    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        levelCreator = new LevelCreator();
        levelCreator.generateObjects();
        character = levelCreator.getCharacter();
        gameObjects = levelCreator.generateObstacles(1);
        titleScreen = new Texture("title screen.png");
        backMusic = Gdx.audio.newMusic(Gdx.files.internal("the_field_of_dreams.mp3"));

        //Generator för att läsa in en font till bitmapFont
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 41;
        highScoreFont = generator.generateFont(parameter);
        timeLeftFont = generator.generateFont(parameter);
        generator.dispose();

        highScoreFont.setColor(Color.FIREBRICK);
        timeLeftFont.setColor(Color.FIREBRICK);

        level1 = new Level1Map();
    }

    @Override
    public void render() {
        if (gameState == GameState.TITLE_SCREEN) {
            setTitleScreen();
        } else if (gameState == GameState.LEVEL1_SCREEN) {

		/*
        Testkod ovanför för att få något meny-aktigt */

            if (time > 0) {
                time -= Gdx.graphics.getDeltaTime();
            }
            checkInput();

            isOnGround = false;
            for (MapObject object : level1.getMap().getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                if (character.collidesWith(rectangle)) {
                    //Kollar från vilket håll karaktären kolliderar med ett block.
                    if (character.getY() >= ((rectangle.getY() + rectangle.getHeight()) - 40)) { //Uppifrån
                        if (!Gdx.input.justTouched()) {
                            character.setY(rectangle.getY() + rectangle.getHeight() - 15);
                            isOnGround = true;
                        }
                    } else if (((character.getX() + character.getWidth() + 20) >= rectangle.getX())
                            && (character.getX() + character.getWidth() - 22) < (rectangle.getX() + rectangle.getWidth())) { //Karaktären kommer från höger
                        character.setX(rectangle.getX() - (character.getWidth() - 20));
                    } else if ((character.getX() + 40) >= (rectangle.getX() + rectangle.getWidth())) { //Karaktären kommer från vänster.
                        character.setX((rectangle.getX() + rectangle.getWidth()) - 22);
                    }
                }
            }
            if (isOnGround) {
                character.setCurrentState(Character.JumpState.STATIONARY);
                character.setSpeedY(0);
            } else {
                character.setCurrentState(Character.JumpState.DESCENDING);
            }

            character.updatePositionFromSpeed();
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            level1.render(Gdx.graphics.getDeltaTime());
            batch.begin();

            character.draw(batch);
            for (GameObject object : gameObjects) {
                object.draw(batch);
            }
		highScoreFont.draw(batch, highScore + score, 5, Gdx.graphics.getHeight()-5);
		timeLeftFont.draw(batch, timeLeft + String.format(java.util.Locale.US,"%.1f",time), Gdx.graphics.getWidth()-250, Gdx.graphics.getHeight()-5);

            batch.end();

        } else if (gameState == GameState.LEVEL2_SCREEN) {

        } else if (gameState == GameState.LEVEL3_SCREEN) {

        } else if (gameState == GameState.GAMEOVER_SCREEN) {

        }

    }

    //titlescreen ska leda till själva spelet, tom tills vi har level att lägga in.
    public void setTitleScreen() {
        backMusic.play();
        //backMusic.setLooping(true);
        batch.begin();
        batch.draw(titleScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.isTouched()) {
            backMusic.stop();
            backMusic.play();
            gameState = GameState.LEVEL1_SCREEN;
        }

        batch.end();
    }


    private void checkInput() {

        //highScore=String.valueOf(character.getCurrentState());
        if (Gdx.input.justTouched()) {
            character.jump();
        }

        //Kollar sensorn och avgör hastigheten i X-led därefter. tiltTrigger används som gränsvärde för att kunna stå stilla.
        if (Gdx.input.getAccelerometerY() < -tiltTrigger || Gdx.input.getAccelerometerY() > tiltTrigger) {
            character.setSpeedX((float) ((Gdx.input.getAccelerometerY()) * 2.5));
        } else {
            character.setSpeedX(0);
        }
    }

    @Override
    public void dispose() {
        highScoreFont.dispose();
        batch.dispose();
        titleScreen.dispose();
        backMusic.dispose();
    }
}
