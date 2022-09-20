package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;

public class ConcatListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            String strA = (String) a.getUserData();
            String strB = (String) b.getUserData();
            if (strA.equals("hero") || strB.equals("hero")) {
                if (strB.equals("trap")) GameScreen.action = Action.ROOT;
                if (strA.equals("trap")) GameScreen.action = Action.ROOT;
                if (strB.equals("death")) GameScreen.action = Action.DEATH;
                if (strA.equals("death")) GameScreen.action = Action.DEATH;
                if (strA.equals("finish")) GameScreen.action = Action.WIN;
                if (strB.equals("finish")) GameScreen.action = Action.WIN;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
