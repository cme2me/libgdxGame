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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Action;
import com.mygdx.game.AnimationCl;
import com.mygdx.game.Main;
import com.mygdx.game.PhyX;

import java.util.ArrayList;

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
    private final PhyX physX;
    private Body heroBody;
    public static ArrayList<Body> bodyArrayList;
    public static Action action;
    private boolean stillPlaying;
    private Texture img;

    public GameScreen(Main game) {
        this.main = game;
        TmxMapLoader tm = new TmxMapLoader();
        map = tm.load("map/MarshMap.tmx");
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
        this.physX = new PhyX();
    }
    private void loadHero(){
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("setting").getObjects().get("character");
        this.heroBody = physX.addObject(tmp, "character", true);

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
    public void destroy(){
        for (Body body:
                bodyArrayList) {
            physX.destroyBody(body);
        }
        bodyArrayList.clear();
    }
    private void gameWin() {

        this.stillPlaying = false;
        this.img = new Texture("playerWon.png");
    }
    private void roots() {
        Vector2 currentVector = heroBody.getLinearVelocity();
        heroBody.applyForceToCenter(new Vector2( -100 * currentVector.x, -100 * currentVector.y),
                true);
    }
    private void gameOver() {
        this.stillPlaying = false;
        this.img = new Texture("defeat.png");
    }

    private void doActions() {
        if (action != null) {
            switch (action) {
                case ROOT:
                    roots();
                    break;
                case WIN:
                    gameWin();
                    break;
                case DEATH:
                    gameOver();
                    break;
            }
            action = null;
        }
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
