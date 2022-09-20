package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhyX {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    public PhyX() {

        this.world = new World(new Vector2(0, -5), true);

        this.debugRenderer = new Box2DDebugRenderer();
    }


    public void step(){
        world.step(1/60f, 5, 5);
    }

    public void draw(OrthographicCamera camera){
        debugRenderer.render(world, camera.combined);
    }

    public void dispose(){
        world.dispose();
        debugRenderer.dispose();
    }

    public Body addObject(RectangleMapObject obj, String objName, boolean addNPC){
        return prepareBody(obj, objName,1.5f, 5f,  addNPC);
    }

    public void addTextures(RectangleMapObject obj){
        prepareBody(obj, "textures", 0.4f, 1f,  false);
    }

    public Body prepareBody(RectangleMapObject object, String objName,
                            float friction, float density,  boolean addSensor) {
        Rectangle rect = object.getRectangle();
        String type = (String) object.getProperties().get("Body");
        float gravity = (Float) object.getProperties().get("gravity");

        BodyDef def = new BodyDef();
        FixtureDef fixDef = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rect.width/2, rect.height/2);

        fixDef.shape = polygonShape;
        fixDef.friction = friction;
        fixDef.density = density;

        def.type = BodyDef.BodyType.valueOf(type);
        def.position.set(rect.x + rect.width/2, rect.y + rect.height/2);
        def.gravityScale = gravity;

        Body body;
        body = world.createBody(def);
        body.createFixture(fixDef).setUserData(objName);
        if (addSensor) {
            polygonShape.setAsBox(rect.width/10, rect.height/10,
                    new Vector2(0, -rect.width/2), 0);
            body.createFixture(fixDef).setUserData(objName);
            body.getFixtureList().get(body.getFixtureList().size-1).setSensor(true);
        }

        polygonShape.dispose();
        return body;
    }

    public void destroyBody(Body body){
        world.destroyBody(body);
    }
}
