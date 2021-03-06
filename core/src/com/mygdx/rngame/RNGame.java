package com.mygdx.rngame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;


public class RNGame extends ApplicationAdapter {
    private double tiltTrigger = 0.65;
    private Character character;
    private SpriteBatch batch;
    private Texture gameOverImg;
    private Texture winImg;
    private Texture shareButtonTexture;
    private LevelCreator levelCreator;
    private ArrayList<GameObject> gameObjects;
    private Texture titleScreen;
    private Music backMusic;
    private Sound deathMusic;
    private GameState gameState = GameState.TITLE_SCREEN;
    private LevelMap level1;
    private LevelMap level2;
    private LevelMap level3;
    private boolean isOnGround;
    private DatabaseConnection databaseConnection;

    //Använde dessa till testing, händigare än logcat IMHO.
    private BitmapFont highScoreFont;
    private BitmapFont timeLeftFont;
    private String highScore = "Highscore is: ";
    private String timeLeftText = "Time left: ";
    private int prevHighScore;
    private int score = 0;
    private float time = 10;
    private boolean newHighScore;

    //Meny-test
    private enum GameState {
        TITLE_SCREEN,
        LEVEL1_SCREEN,
        LEVEL2_SCREEN,
        LEVEL3_SCREEN,
        GAMEOVER_SCREEN,
        COMPLETED_GAME_SCREEN
    }

    //Connectar till appens ID och api
    private static final String API_APP_ID = "405872543173805";
    private FacebookApi facebookApi;

    private void setupFbApiInst() {
        facebookApi = new FacebookApi(API_APP_ID);
    }

    //Konstruktor för att kunna länka Databas Connection till AndroidLauncher
    public RNGame(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        levelCreator = new LevelCreator();
        levelCreator.generateObjects();
        character = levelCreator.getCharacter();
        gameObjects = levelCreator.generateObstacles(1);
        titleScreen = new Texture("title screen.png");
        gameOverImg = new Texture("gameover_screen.png");
        winImg = new Texture("winner_screen.png");
        shareButtonTexture = new Texture("button_post_enabled.png");
        backMusic = Gdx.audio.newMusic(Gdx.files.internal("the_field_of_dreams.mp3"));
        deathMusic = Gdx.audio.newSound(Gdx.files.internal("deathsplat2.mp3"));
        prevHighScore = Integer.valueOf(databaseConnection.getScore());
        newHighScore = false;

        //Generator för att läsa in en font till bitmapFont
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 41;
        highScoreFont = generator.generateFont(parameter);
        timeLeftFont = generator.generateFont(parameter);
        generator.dispose();

        setupFbApiInst();

        highScoreFont.setColor(Color.FIREBRICK);
        timeLeftFont.setColor(Color.FIREBRICK);

        level1 = new LevelMap(1);
        level2 = new LevelMap(2);
        level3 = new LevelMap(3);
    }

    @Override
    public void render() {

        if (gameState == GameState.TITLE_SCREEN) {
            setTitleScreen();
        } else if (gameState == GameState.LEVEL1_SCREEN) {
            renderLevel();

        } else if (gameState == GameState.LEVEL2_SCREEN) {
            renderLevel();

        } else if (gameState == GameState.LEVEL3_SCREEN) {
            renderLevel();

        } else if (gameState == GameState.GAMEOVER_SCREEN) {
            renderGameOver();
        } else if (gameState == GameState.COMPLETED_GAME_SCREEN){
            renderWinningScreen();
        }

    }

    public void renderLevel(){
        if (time > 0) {
            time -= Gdx.graphics.getDeltaTime();
        } else {
            gameState=GameState.GAMEOVER_SCREEN;
            resetCharacter(); //resets position of character
            resetTime();
            gameObjects = levelCreator.generateObstacles(1);
        }
        checkInput();

        if (gameState == GameState.LEVEL1_SCREEN){
            platformDetection(1);
        } else if (gameState == GameState.LEVEL2_SCREEN){
            platformDetection(2);
        } else if (gameState == GameState.LEVEL3_SCREEN){
            platformDetection(3);
        }

        for (GameObject gameObject: gameObjects){
            if (character.collidesWith(gameObject.getBoundingRectangle())){
                if (((Obstacle) gameObject).isEvil()){
                    gameState=GameState.GAMEOVER_SCREEN;
                    backMusic.stop();
                    deathMusic.play();
                    resetCharacter(); //resets position of character
                    resetTime();
                    gameObjects = levelCreator.generateObstacles(1);


                } else {
                    if (gameState == GameState.LEVEL1_SCREEN){
                        score = (int) time * 10;
                        resetCharacter();
                        resetTime();
                        gameState = GameState.LEVEL2_SCREEN;
                        gameObjects = levelCreator.generateObstacles(2);

                    } else if (gameState == GameState.LEVEL2_SCREEN){
                        score += (int) time * 10;
                        resetCharacter();
                        resetTime();
                        gameState = GameState.LEVEL3_SCREEN;
                        gameObjects = levelCreator.generateObstacles(3);

                    } else if (gameState == GameState.LEVEL3_SCREEN){
                        score += (int) time * 10;
                        resetCharacter();
                        resetTime();
                        gameState = GameState.COMPLETED_GAME_SCREEN;
                        gameObjects = levelCreator.generateObstacles(1);

                    }
                }
            }
        }


        character.updatePositionFromSpeed();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameState == GameState.LEVEL1_SCREEN){
            level1.render(Gdx.graphics.getDeltaTime());
        } else if (gameState == GameState.LEVEL2_SCREEN){
            level2.render(Gdx.graphics.getDeltaTime());
        } else if (gameState == GameState.LEVEL3_SCREEN){
            level3.render(Gdx.graphics.getDeltaTime());
        }
        batch.begin();
        character.draw(batch);
        for (GameObject object : gameObjects) {
            object.draw(batch);
        }
        highScoreFont.draw(batch, highScore + prevHighScore, 5, Gdx.graphics.getHeight()-5);
        timeLeftFont.draw(batch, timeLeftText + String.format(java.util.Locale.US,"%.1f",time), Gdx.graphics.getWidth()-250, Gdx.graphics.getHeight()-5);

        batch.end();
    }

    public void resetCharacter(){
        character.setX(0);
        character.setY(140);
        character.setSpeedY(0);
        character.setSpeedX(0);

    }

    public void resetTime(){
        time = 10;
    }

    private void renderWinningScreen() {
        //Kollar om man slagit High Score
        if (score > prevHighScore){
            databaseConnection.setScore(String.valueOf(score));
            newHighScore = true;
        }

        batch.begin();
        batch.draw(winImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (newHighScore){
            batch.draw(shareButtonTexture,Gdx.graphics.getWidth()/2-253,200, 253*2,40*2);
        }

        if (Gdx.input.justTouched()) {
            if (newHighScore){
                if((Gdx.input.getX() >= (Gdx.graphics.getWidth()/2)-253 && Gdx.input.getX() <= (Gdx.graphics.getWidth()/2)+253) && (Gdx.input.getY() <= Gdx.graphics.getHeight()-200 && Gdx.input.getY() >= Gdx.graphics.getHeight()-240)){
                    facebookApi.sharePost();
                } else {
                    prevHighScore = Integer.valueOf(databaseConnection.getScore()); //Hämtar high score på nytt.
                    gameState = GameState.TITLE_SCREEN;
                }
                newHighScore = false;
            } else {
                prevHighScore = Integer.valueOf(databaseConnection.getScore()); //Hämtar high score på nytt.
                gameState = GameState.TITLE_SCREEN;
            }
        }

        batch.end();
    }

    public void renderGameOver(){
        //Kollar om man slagit High Score
        if (score > prevHighScore){
            databaseConnection.setScore(String.valueOf(score));
            newHighScore = true;
        }

        batch.begin();
        batch.draw(gameOverImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (newHighScore){
            batch.draw(shareButtonTexture,Gdx.graphics.getWidth()/2-253,200, 253*2,40*2);
        }
        if (Gdx.input.justTouched()) {
            if (newHighScore){
                if((Gdx.input.getX() >= (Gdx.graphics.getWidth()/2)-253 && Gdx.input.getX() <= (Gdx.graphics.getWidth()/2)+253) && (Gdx.input.getY() <= Gdx.graphics.getHeight()-200 && Gdx.input.getY() >= Gdx.graphics.getHeight()-240)){
                    facebookApi.sharePost();
                } else {
                    prevHighScore = Integer.valueOf(databaseConnection.getScore()); //Hämtar high score på nytt.
                    gameState = GameState.TITLE_SCREEN;
                }
                newHighScore = false;
            } else {
                prevHighScore = Integer.valueOf(databaseConnection.getScore()); //Hämtar high score på nytt.
                gameState = GameState.TITLE_SCREEN;
            }
        }

        batch.end();
    }

    //titlescreen ska leda till själva spelet, tom tills vi har level att lägga in.
    public void setTitleScreen() {
        backMusic.play();
        //backMusic.setLooping(true);
        batch.begin();
        batch.draw(titleScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.justTouched()) {
            facebookApi.publishLogin();
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

    private void platformDetection(int level){
        isOnGround = false;
        if (level == 1){
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
        } else if (level == 2){
            for (MapObject object : level2.getMap().getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
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
        } else if (level == 3){
            for (MapObject object : level3.getMap().getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
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
        }
    }

    @Override
    public void dispose() {
        highScoreFont.dispose();
        batch.dispose();
        titleScreen.dispose();
        backMusic.dispose();
        deathMusic.dispose();
    }
}
