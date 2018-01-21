package com.ldz.itf;

import com.badlogic.gdx.physics.box2d.ContactListener;

public interface ITetrisPhysicsContactGeneration {

    @Deprecated
    void createAndRegisterBlockContact();

    void addContactListener(ContactListener contactListener);

}
