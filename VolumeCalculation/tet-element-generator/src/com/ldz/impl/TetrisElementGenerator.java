package com.ldz.impl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ldz.itf.*;
import com.ldz.tet.shape.debugger.impl.ShapeDebugger;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TetrisElementGenerator implements ITetrisElementGenerator {

    private static ITetrisElementGenerator instance;

    private IPhysicsGeneration iPhysicsGeneration;
    private IShapeDebugger iShapeDebugger;

    private TetrisElementGenerator() {
        this.iPhysicsGeneration = PhysicsGeneration.getInstance();
    }

    public static ITetrisElementGenerator getInstance() {
        if (instance == null) {
            instance = new TetrisElementGenerator();
        }
        return instance;
    }

    @Override
    public TetrisElement createTetrisElement(List<Vector2> worldPositions, float tetrisGridWidth,
                                             TetrisElement tetrisElement) {

        List<TetrisBlock> tetrisBlocks = new ArrayList<>();

        Body body = iPhysicsGeneration.createTetrisElementBody(worldPositions, tetrisGridWidth, tetrisGridWidth);
        tetrisElement.setBody(body);
        tetrisElement.setPlayerOwningState(TetrisElementDomainContants.PLAYER_OWNED);
        body.setUserData(tetrisElement);

        tetrisElement.setInitialOrientation(new Vector2(body.getTransform().getOrientation()));

        for (Vector2 positions :
                worldPositions) {
            tetrisBlocks.add(new TetrisBlock());
        }

        //set fixtures
        for (TetrisBlock tetrisBlock :
                tetrisBlocks) {
            int index = tetrisBlocks.indexOf(tetrisBlock);
            tetrisBlock.setFixture(body.getFixtureList().get(index));
        }


        if (this.iShapeDebugger == null) {
            this.iShapeDebugger = ShapeDebugger.getInstance();
        }

        tetrisElement.setTetrisBlocks(tetrisBlocks);
        return tetrisElement;
    }
}
