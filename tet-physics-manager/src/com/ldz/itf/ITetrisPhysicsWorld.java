package com.ldz.itf;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ContactListener;

public interface ITetrisPhysicsWorld {

    void initWorld();

    void updatePhysics(float delta);

    Body registerBody(BodyDef bodyDef);

    void registerContactListener(ContactListener contactListener);

    void render(Camera camera);
}
