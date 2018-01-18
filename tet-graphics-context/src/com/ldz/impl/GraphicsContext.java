package com.ldz.impl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ldz.itf.IGraphicContext;

import java.util.List;

/**
 * <p>
 * Technical parameters owned :
 * <li>{@link GraphicsContext#spriteBatch} : allow rendering {@link Sprite}</li>
 * </p>
 */
public class GraphicsContext implements IGraphicContext {

    private static IGraphicContext instance;

    private SpriteBatch spriteBatch;

    private GraphicsContext() {
    }

    private GraphicsContext(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public static IGraphicContext getInstance() {
        if (instance == null) {
            instance = new GraphicsContext();
        }
        return instance;
    }

    public static IGraphicContext getInstance(SpriteBatch spriteBatch) {
        if (instance == null) {
            instance = new GraphicsContext(spriteBatch);
        }
        return instance;
    }

    @Override
    public void render(List<Sprite> sprites) {
        for (Sprite sprite :
                sprites) {
            sprite.draw(this.spriteBatch);
        }
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
