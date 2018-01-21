package com.ldz.impl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;

public class TetrisGrid implements ITetrisGrid {

    private static ITetrisGrid instance;
    private ITetrisPhysicsWorld iTetrisPhysicsWorld;
    private IGraphicContext iGraphicContext;
    private ITetElementToPositionnedSprite iTetElementToPositionnedSprite;
    private OutboundDestroyer outboundDestroyer;

    private final Pool<TetrisElement> tetrisElementPool = new Pool<TetrisElement>() {
        @Override
        protected TetrisElement newObject() {
            return new TetrisElement();
        }
    };

    private List<TetrisElement> tetrisElements = new ArrayList<>();

    private TetrisGrid() {
        this.iTetElementToPositionnedSprite = TetElementToPositionnedSprite.getInstance();
    }

    public static ITetrisGrid getInstance() {
        if (instance == null) {
            instance = new TetrisGrid();
        }
        return instance;
    }

    @Override
    public boolean addTetrisElement(TetrisElement tetrisElement) {
        return this.tetrisElements.add(tetrisElement);
    }

    @Override
    public void renderAndUpdate(float delta) {

        if (this.iGraphicContext == null) {
            this.iGraphicContext = GraphicsContext.getInstance();
        }
        if (this.iTetrisPhysicsWorld == null) {
            this.iTetrisPhysicsWorld = TetrisWorld.getInstance();
        }

        this.iTetrisPhysicsWorld.updatePhysics(delta);

        List<Sprite> spriteToRender = this.getSpriteToRender();
        this.iGraphicContext.render(spriteToRender);
    }

    private List<Sprite> getSpriteToRender() {
        List<Sprite> sprites = new ArrayList<>();
        for (TetrisElement tetrisElement :
                this.tetrisElements) {
            sprites.addAll(this.iTetElementToPositionnedSprite.getSpritesFromTetrisElement(tetrisElement));
        }
        return sprites;
    }

    private List<Vector2> gridCoordinates() {
        List<Vector2> positions = new ArrayList<>();
        for (int i = 0; i < TetrisGridContants.NB_BLOCK_WIDTH; i++) {
            for (int j = 0; j < TetrisGridContants.NB_BLOCK_HEIGHT; j++) {
                positions.add(new Vector2(i * TetrisGridContants.GRID_BLOCK_SIZE, j * TetrisGridContants.GRID_BLOCK_SIZE));
            }
        }
        return positions;
    }

    @Override
    public void setOutboundDestroyer(OutboundDestroyer outboundDestroyer) {
        this.outboundDestroyer = outboundDestroyer;
    }

    @Override
    public void deleteElementFromGrid(TetrisElement tetrisElement) {
        this.tetrisElementPool.free(tetrisElement);
        this.tetrisElements.remove(tetrisElement);
    }

    @Override
    public TetrisElement getNewTetrisElementFromPool() {
        return this.tetrisElementPool.obtain();
    }
}
