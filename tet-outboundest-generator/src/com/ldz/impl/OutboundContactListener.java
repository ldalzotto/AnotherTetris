package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.ldz.itf.IElementEraser;
import com.ldz.itf.OutboundDestroyer;
import com.ldz.itf.TetrisElement;

public class OutboundContactListener implements ContactListener {

    private static OutboundContactListener instance;
    public static OutboundContactListener getInstance(){
        if(instance==null){
            instance=new OutboundContactListener();
        }
        return instance;
    }
    private OutboundContactListener(){
        this.iElementEraser = ElementEraser.getInstance();
    };

    private IElementEraser iElementEraser;

    @Override
    public void beginContact(Contact contact) {
        //get bodies
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        //getUserData
        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        if(userDataA instanceof OutboundDestroyer || userDataB instanceof OutboundDestroyer){
            //get non destroyer
            if(!(userDataA instanceof OutboundDestroyer) && userDataA instanceof TetrisElement){
                this.iElementEraser.addElementToErase((TetrisElement) userDataA);
            } else if(!(userDataB instanceof OutboundDestroyer) && userDataB instanceof TetrisElement){
                this.iElementEraser.addElementToErase((TetrisElement) userDataB);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
