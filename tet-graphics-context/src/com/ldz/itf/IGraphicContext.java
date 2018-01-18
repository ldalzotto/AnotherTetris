package com.ldz.itf;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

/**
 * <p>
 * This interface is responsible of :
 * <li>Displaying graphics content</li>
 * </p>
 *
 * @author Loïc Dal Zotto
 */
public interface IGraphicContext {

    /**
     * <p>
     * This method
     * </p>
     *
     * @param sprites
     */
    void render(List<Sprite> sprites);

    SpriteBatch getSpriteBatch();

}
