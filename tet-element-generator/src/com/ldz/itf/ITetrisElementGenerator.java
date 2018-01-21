package com.ldz.itf;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface ITetrisElementGenerator {

    TetrisElement createTetrisElement(List<Vector2> worldPositions, float tetrisGridWidth,
                                      TetrisElement tetrisElement);

    @Deprecated
    GridLimits createTetrisGrid(Rectangle bottomPos, Rectangle leftPos, Rectangle rightPos);
}
