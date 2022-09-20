package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AnimationCl;
import com.mygdx.game.Main;

public class GameScreen implements Screen {
    private final Main main;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private final Batch batch;
    private final int[] bg;
    private final int[] layerOne;
    private final Rectangle mapSize;
    private AnimationCl animationCl;

    public GameScreen(Main game) {
        this.main = game;
        TmxMapLoader tm = new TmxMapLoader();
        map = tm.load("map/map.tmx");
        bg = new int[1];
        bg[0] = map.getLayers().getIndex("background");
        layerOne = new int[2];
        layerOne[0] = map.getLayers().getIndex("layer1-0");
        layerOne[1] = map.getLayers().getIndex("layer-1-1");
        animationCl = new AnimationCl("anim", 2, 2, Animation.PlayMode.LOOP);
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        RectangleMapObject mapObject = (RectangleMapObject) map.getLayers().get("objects").getObjects().get("camera");
        mapObject = (RectangleMapObject) map.getLayers().get("objects").getObjects().get("borders");
        mapSize = mapObject.getRectangle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        camera.update();
        renderer.setView(camera);
        renderer.render(bg);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            main.dispose();
            main.setScreen(new MenuScreen(main));
        }
        batch.begin();
        TextureRegion trTmp = animationCl.getFrame();
        batch.draw(trTmp, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
