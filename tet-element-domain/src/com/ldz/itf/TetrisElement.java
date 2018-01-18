package com.ldz.itf;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;
import java.util.List;

public class TetrisElement {

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
}
