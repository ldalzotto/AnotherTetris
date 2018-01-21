package com.ldz.impl;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.ldz.itf.IMultipleContactListener;
import com.ldz.itf.ITetrisPhysicsContactGeneration;
import com.ldz.itf.ITetrisPhysicsWorld;

public class TetrisPhysicsContactGeneration implements ITetrisPhysicsContactGeneration {

    private static ITetrisPhysicsContactGeneration instance;
    private IMultipleContactListener iMultipleContactListener;
    private   ITetrisPhysicsWorld iTetrisPhysicsWorld;

    private TetrisPhysicsContactGeneration() {
        iMultipleContactListener = MultipleContactListener.getInstance();
        iTetrisPhysicsWorld = TetrisWorld.getInstance();
    }

    public static ITetrisPhysicsContactGeneration getInstance() {
        if (instance == null) {
            instance = new TetrisPhysicsContactGeneration();
        }
        return instance;
    }

    @Override
    public void createAndRegisterBlockContact() {

        this.iMultipleContactListener.addContactListener(PlayerElementContactListener.getInstance());

        iTetrisPhysicsWorld.registerContactListener((ContactListener) this.iMultipleContactListener);

    }

    @Override
    public void addContactListener(ContactListener contactListener) {
        this.iMultipleContactListener.addContactListener(contactListener);
        iTetrisPhysicsWorld.registerContactListener((ContactListener) this.iMultipleContactListener);
    }
}
