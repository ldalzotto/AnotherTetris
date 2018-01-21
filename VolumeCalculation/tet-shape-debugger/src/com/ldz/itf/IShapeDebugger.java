package com.ldz.itf;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.function.Function;

public interface IShapeDebugger {

    void render(Camera camera, float delta);

    boolean pushDrawEvent(Function<ShapeRenderer, Void> event, float time);

    boolean isEnabled();
}
