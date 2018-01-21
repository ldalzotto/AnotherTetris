package com.ldz.impl;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ldz.itf.IMultipleContactListener;

import java.util.ArrayList;
import java.util.List;

public class MultipleContactListener implements IMultipleContactListener, ContactListener {


    private static IMultipleContactListener instance;
    public static IMultipleContactListener getInstance(){
        if(instance==null){
            instance = new MultipleContactListener();
        }
        return instance;
    }
    private MultipleContactListener(){};

    private List<ContactListener> activecContactListeners = new ArrayList<>();

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener contactListener :
                this.activecContactListeners) {
            contactListener.beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener contactListener :
                this.activecContactListeners) {
            contactListener.endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        for (ContactListener contactListener :
                this.activecContactListeners) {
            contactListener.preSolve(contact, manifold);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        for (ContactListener contactListener :
                this.activecContactListeners) {
            contactListener.postSolve(contact, contactImpulse);
        }
    }

    public List<ContactListener> getActivecContactListeners() {
        return activecContactListeners;
    }

    public void setActivecContactListeners(List<ContactListener> activecContactListeners) {
        this.activecContactListeners = activecContactListeners;
    }

    @Override
    public boolean addContactListener(ContactListener contactListener) {
        if(!this.activecContactListeners.contains(contactListener)){
            return this.activecContactListeners.add(contactListener);
        }
        return false;
    }
}
