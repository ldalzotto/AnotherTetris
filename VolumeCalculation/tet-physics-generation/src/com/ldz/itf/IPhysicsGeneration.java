package com.ldz.itf;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.List;

public interface IPhysicsGeneration {

    Body createTetrisElementBody(List<Vector2> worldPositions, float blockWidht, float blocHeight);

    @Deprecated
    Body createGridLimitRectangle(Vector2 worldPosition, float width, float height);

    Body createRectangleBody(Vector2 worldPosition, float width, float height);
}
