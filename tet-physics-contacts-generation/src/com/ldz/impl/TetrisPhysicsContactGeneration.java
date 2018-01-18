package com.ldz.impl;

import com.ldz.itf.ITetrisPhysicsContactGeneration;
import com.ldz.itf.ITetrisPhysicsWorld;

public class TetrisPhysicsContactGeneration implements ITetrisPhysicsContactGeneration {

    private static ITetrisPhysicsContactGeneration instance;

    private TetrisPhysicsContactGeneration() {
    }

    public static ITetrisPhysicsContactGeneration getInstance() {
        if (instance == null) {
            instance = new TetrisPhysicsContactGeneration();
        }
        return instance;
    }

    @Override
    public void createAndRegisterBlockContact() {

        ITetrisPhysicsWorld iTetrisPhysicsWorld = TetrisWorld.getInstance();
        iTetrisPhysicsWorld.registerContactListener(PlayerElementContactListener.getInstance());

    }
}
