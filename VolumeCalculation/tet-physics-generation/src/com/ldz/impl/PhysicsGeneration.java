
package com.ldz.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ldz.itf.IPhysicsGeneration;
import com.ldz.itf.ITetrisPhysicsWorld;

import java.util.List;

public class PhysicsGeneration implements IPhysicsGeneration {

    private static Vector2 BLOCK_VELOCITY = new Vector2(0, -100);

    private static IPhysicsGeneration instance;


    private PhysicsGeneration() {
    }

    public static IPhysicsGeneration getInstance() {
        if (instance == null) {
            instance = new PhysicsGeneration();
        }
        return instance;
    }

    @Override
    public Body createTetrisElementBody(List<Vector2> worldPositions, float blockWidht, float blocHeight) {

        ITetrisPhysicsWorld iTetrisPhysicsWorld = TetrisWorld.getInstance();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Vector2 origin = worldPositions.get(0);

        Body body = iTetrisPhysicsWorld.registerBody(bodyDef);
        body.setTransform(origin, 0);

        for (Vector2 position :
                worldPositions) {

            PolygonShape polyGonShape = new PolygonShape();
            polyGonShape.setAsBox(blockWidht / 2, blocHeight / 2, new Vector2(position.x - origin.x, position.y - origin.y), 0);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polyGonShape;
            Fixture fixture = body.createFixture(polyGonShape, 100000f);
            fixture.setRestitution(0f);
            polyGonShape.dispose();

        }
        body.setLinearVelocity(BLOCK_VELOCITY);

        return body;
    }

    @Override
    public Body createGridLimitRectangle(Vector2 worldPosition, float width, float height) {
        ITetrisPhysicsWorld iTetrisPhysicsWorld = TetrisWorld.getInstance();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = iTetrisPhysicsWorld.registerBody(bodyDef);

        body.setTransform(worldPosition, 0);

        PolygonShape polyGonShape = new PolygonShape();
        polyGonShape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polyGonShape;
        Fixture fixture = body.createFixture(polyGonShape, 100000f);
        fixture.setRestitution(0f);
        polyGonShape.dispose();

        return body;
    }

    @Override
    public Body createRectangleBody(Vector2 worldPosition, float width, float height) {
        return this.createGridLimitRectangle(worldPosition, width, height);
    }
}
