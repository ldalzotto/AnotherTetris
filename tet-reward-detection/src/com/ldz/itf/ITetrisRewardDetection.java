package com.ldz.itf;

import com.badlogic.gdx.math.Vector2;

public interface ITetrisRewardDetection {

    void checkRewardAndSpawn();

    void addRewardLine(Vector2 beginPosition, Vector2 endPosition);
}
