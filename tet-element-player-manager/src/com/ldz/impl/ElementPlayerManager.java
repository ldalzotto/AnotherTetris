package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.ldz.itf.*;

public class ElementPlayerManager implements IElementPlayerManager {

    private static IElementPlayerManager instance;
    private IGraphicContext iGraphicContext;
    private ITetrisGrid iTetrisGrid;
    private TetrisElement tetrisElement;

    private ElementPlayerManager() {
        this.iGraphicContext = GraphicsContext.getInstance();
        this.iTetrisGrid = TetrisGrid.getInstance();
    }

    public static IElementPlayerManager getInstance() {
        if (instance == null) {
            instance = new ElementPlayerManager();
        }
        return instance;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            this.displaceAllElements(new Vector2(-TetrisGridContants.GRID_BLOCK_SIZE, 0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            this.displaceAllElements(new Vector2(TetrisGridContants.GRID_BLOCK_SIZE, 0));
        }
    }

    @Override
    public void pushElementToGrid() {
        this.iTetrisGrid.addTetrisElement(this.tetrisElement);
        this.tetrisElement = null;
    }

    @Override
    public void setPlayerElement(TetrisElement playerElement) {
        this.tetrisElement = playerElement;
    }

    @Override
    public TetrisElement getPlauerElement() {
        return this.tetrisElement;
    }

    private void displaceAllElements(Vector2 dVect) {
        if (this.tetrisElement != null) {
            this.tetrisElement.getBody().setTransform(tetrisElement.getBody().getPosition().add(dVect), 0);
        }
    }
}
