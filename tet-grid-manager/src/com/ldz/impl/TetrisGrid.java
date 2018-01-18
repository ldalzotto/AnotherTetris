package com.ldz.impl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;

public class TetrisGrid implements ITetrisGrid {

    private static ITetrisGrid instance;
    public BottomLine bottomLine;
    private ITetrisPhysicsWorld iTetrisPhysicsWorld;
    private IGraphicContext iGraphicContext;
    private ITetElementToPositionnedSprite iTetElementToPositionnedSprite;
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

    @Override
    public void addBottomLine(BottomLine bottomLine) {
        this.bottomLine = bottomLine;
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
    public BottomLine getBottomLine() {
        return this.bottomLine;
    }

    @Override
    public boolean isInField(TetrisElement tetrisElement) {
        return this.tetrisElements.contains(tetrisElement);
    }
}
