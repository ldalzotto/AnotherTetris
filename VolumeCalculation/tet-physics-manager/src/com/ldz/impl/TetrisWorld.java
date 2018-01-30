package com.ldz.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ldz.itf.ITetrisPhysicsWorld;
import com.ldz.tet.physics.debugger.impl.PhysicsDebugger;
import com.ldz.tet.physics.debugger.itf.IPhysicsDebugger;

public class TetrisWorld implements ITetrisPhysicsWorld {

    private static TetrisWorld instance;

    private float TIME_STEP = 1 / 60f;
    private float accumulator = 0;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private IPhysicsDebugger iPhysicsDebugger = PhysicsDebugger.getInstance();

    private TetrisWorld() {
    }

    public static TetrisWorld getInstance() {
        if (instance == null) {
            instance = new TetrisWorld();
            instance.initWorld();
        }
        return instance;
    }

    @Override
    public void initWorld() {
        this.world = new World(new Vector2(0, -90), true);
        if (this.iPhysicsDebugger.isDebugEnabled()) {
            this.box2DDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        }
    }

    @Override
    public void updatePhysics(float delta) {
        if (this.world != null) {
            float frameTime = Math.min(delta, 0.25f);
            accumulator += frameTime;
            while (accumulator >= TIME_STEP) {
                this.world.step(TIME_STEP, 6, 2);
                accumulator -= TIME_STEP;
            }
        }
    }

    @Override
    public void render(Camera camera) {
        if (this.iPhysicsDebugger.isDebugEnabled()) {
            //  this.box2DDebugRenderer.render(this.world, camera.combined);
        }
    }

    @Override
    public Body registerBody(BodyDef bodyDef) {
        return this.world.createBody(bodyDef);
    }

    @Override
    public void registerContactListener(ContactListener contactListener) {
        this.world.setContactListener(contactListener);
    }

    @Override
    public void removeBody(Body body) {
        if (body != null) {
            this.world.destroyBody(body);
        }
    }
}
