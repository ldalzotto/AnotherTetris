package com.ldz.itf;

import com.badlogic.gdx.physics.box2d.ContactListener;

public interface IMultipleContactListener {

    boolean addContactListener(ContactListener contactListener);

}
