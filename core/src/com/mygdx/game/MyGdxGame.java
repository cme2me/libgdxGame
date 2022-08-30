package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    int click;
    AnimationCl animationCl;
    boolean dir;
    private boolean lookRight = true;
    private int animPositionX = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        animationCl = new AnimationCl("hero_spritesheet.png", 9, 6, Animation.PlayMode.LOOP);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        animationCl.setTime(Gdx.graphics.getDeltaTime());
        float x = Gdx.graphics.getWidth() / 2f ;
        float y = Gdx.graphics.getHeight() - Gdx.input.getY() - animationCl.getFrame().getRegionHeight()/2.0f;

        if (Gdx.input.isButtonJustPressed(Input.Keys.A)) {
            dir = true;
        }
        if (Gdx.input.isButtonJustPressed(Input.Keys.D)) {
            dir = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) lookRight = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) lookRight = true;
        if (animPositionX + 128 >= Gdx.graphics.getWidth()) lookRight = false;
        if (animPositionX <= 0) lookRight = true;

        if (!animationCl.getFrame().isFlipX() && !dir) {
            animationCl.getFrame().flip(true, false);
        }
        if (animationCl.getFrame().isFlipX() && dir) {
            animationCl.getFrame().flip(false, false);
        }

        batch.begin();
        batch.draw(animationCl.getFrame(), x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        animationCl.dispose();
    }
}