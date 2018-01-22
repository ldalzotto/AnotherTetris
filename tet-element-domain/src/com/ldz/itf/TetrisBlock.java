package com.ldz.itf;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pool;

public class TetrisBlock implements Pool.Poolable {

    private Vector2 worldPosition;
    private Sprite sprite;
    private Fixture fixture;

    private TetrisElement tetrisElementReference;

    public Vector2 getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(Vector2 worldPosition) {
        this.worldPosition = worldPosition;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public TetrisElement getTetrisElementReference() {
        return tetrisElementReference;
    }

    public void setTetrisElementReference(TetrisElement tetrisElementReference) {
        this.tetrisElementReference = tetrisElementReference;
    }

    @Override
    public void reset() {
        this.worldPosition = null;
        this.fixture = null;
        this.getSprite().getTexture().dispose();
    }
}
