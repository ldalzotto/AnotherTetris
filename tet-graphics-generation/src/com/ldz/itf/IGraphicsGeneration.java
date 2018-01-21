package com.ldz.itf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface IGraphicsGeneration {

    List<Sprite> createTetrisBlockSprite(List<Vector2> worldPositions, float width);

    Sprite createBakgroundBlockSprite(Vector2 worldPosition);
}
