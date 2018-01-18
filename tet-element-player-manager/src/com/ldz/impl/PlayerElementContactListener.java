package com.ldz.impl;

import com.badlogic.gdx.physics.box2d.*;
import com.ldz.itf.IElementPlayerManager;
import com.ldz.itf.TetrisElement;
import com.ldz.itf.TetrisElementDomainContants;

public class PlayerElementContactListener implements ContactListener {

    private static ContactListener instance;
    private IElementPlayerManager iElementPlayerManager;

    private PlayerElementContactListener() {
        this.iElementPlayerManager = ElementPlayerManager.getInstance();
    }

    public static ContactListener getInstance() {
        if (instance == null) {
            instance = new PlayerElementContactListener();
        }
        return instance;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //get bodies
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        //getUserData
        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        if (userDataA instanceof TetrisElement) {
            TetrisElement tetrisElementA = (TetrisElement) userDataA;
            if (tetrisElementA.getPlayerOwningState().equals(TetrisElementDomainContants.PLAYER_OWNED)) {
                tetrisElementA.setPlayerOwningState(TetrisElementDomainContants.PLANER_NOT_OWNED);
                this.iElementPlayerManager.pushElementToGrid();
            }
        }

        if (userDataB instanceof TetrisElement) {
            TetrisElement tetrisElementB = (TetrisElement) userDataB;
            if (tetrisElementB.getPlayerOwningState().equals(TetrisElementDomainContants.PLAYER_OWNED)) {
                tetrisElementB.setPlayerOwningState(TetrisElementDomainContants.PLANER_NOT_OWNED);
                this.iElementPlayerManager.pushElementToGrid();
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
