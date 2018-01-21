package com.ldz.itf;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

public class TetrisElement implements Pool.Poolable{

    private Body body;
    private List<TetrisBlock> tetrisBlocks = new ArrayList<>();
    private String playerOwningState;
    private Vector2 initialOrientation;

    public List<TetrisBlock> getTetrisBlocks() {
        return tetrisBlocks;
    }

    public void setTetrisBlocks(List<TetrisBlock> tetrisBlocks) {
        this.tetrisBlocks = tetrisBlocks;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getPlayerOwningState() {
        return playerOwningState;
    }

    public void setPlayerOwningState(String playerOwningState) {
        this.playerOwningState = playerOwningState;
    }

    public Vector2 getInitialOrientation() {
        return initialOrientation;
    }

    public void setInitialOrientation(Vector2 initialOrientation) {
        this.initialOrientation = initialOrientation;
    }

    @Override
    public void reset() {
        this.body = null;
        this.initialOrientation = null;
        for (TetrisBlock tetrisBlock :
                this.tetrisBlocks) {
            tetrisBlock.getSprite().getTexture().dispose();
        }
        this.tetrisBlocks = new ArrayList<>();
        this.playerOwningState = null;
    }
}
