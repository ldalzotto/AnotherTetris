package com.ldz.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ldz.itf.IGraphicsGeneration;

import java.util.ArrayList;
import java.util.List;

public class GraphicsGeneration implements IGraphicsGeneration {

    private static IGraphicsGeneration instance;

    private GraphicsGeneration() {
    }

    public static IGraphicsGeneration getInstance() {
        if (instance == null) {
            instance = new GraphicsGeneration();
        }
        return instance;
    }

    @Override
    public List<Sprite> createTetrisBlockSprite(List<Vector2> worldPositions, float width) {

        List<Sprite> returnedSprites = new ArrayList<>();
        Color color = new Color(MathUtils.random(0.1f, 1), MathUtils.random(0.1f, 1), MathUtils.random(0.1f, 1), 0.75f);

        for (Vector2 vector2 :
                worldPositions) {
            Sprite createdSprite = createTetrisBlockSprite(vector2, width, color);
            returnedSprites.add(createdSprite);
        }
        return returnedSprites;
    }

    private Sprite createTetrisBlockSprite(Vector2 worldPosition, float width, Color color) {

        Integer iWidth = Math.round(width);

        Pixmap pixmap = new Pixmap(iWidth, iWidth, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, iWidth, iWidth);
        Sprite sprite = new Sprite(new Texture(pixmap));
        return sprite;
    }

    @Override
    public Sprite createBakgroundBlockSprite(Vector2 worldPosition) {
        return null;
    }
}
