package com.mygdx.rngame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


/**
 * Created by Fidde on 2018-03-13.
 */

public class LevelMap {
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;

    public LevelMap(int level){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        mapLoader = new TmxMapLoader();
        switch (level){
            case 1 :
                map = mapLoader.load("level1.tmx");
                break;
            case 2:
                map = mapLoader.load("level2.tmx");
                break;
            case 3:
                map = mapLoader.load("level3.tmx");
                break;
        }
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public TiledMap getMap() {
        return map;
    }

    public void render(float delta){
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }
}
