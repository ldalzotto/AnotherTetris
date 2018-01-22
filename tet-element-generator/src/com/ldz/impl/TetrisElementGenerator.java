package com.ldz.impl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.ldz.itf.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TetrisElementGenerator implements ITetrisElementGenerator {

    private static ITetrisElementGenerator instance;

    private IPhysicsGeneration iPhysicsGeneration;
    private IGraphicsGeneration iGraphicsGeneration;
    private IShapeDebugger iShapeDebugger;
    private ITetElementPoolable iTetElementPoolable;
    private ITetBlockPoolable iTetBlockPoolable;

    private TetrisElementGenerator() {
        this.iGraphicsGeneration = GraphicsGeneration.getInstance();
        this.iPhysicsGeneration = PhysicsGeneration.getInstance();
        this.iTetElementPoolable = TetElementPoolable.getInstance();
        this.iTetBlockPoolable = TetBlockPoolable.getInstance();
    }

    public static ITetrisElementGenerator getInstance() {
        if (instance == null) {
            instance = new TetrisElementGenerator();
        }
        return instance;
    }

    @Override
    public TetrisElement createTetrisElement(List<Vector2> worldPositions, float tetrisGridWidth) {

        TetrisElement tetrisElement = this.iTetElementPoolable.getElementInstance();
        List<TetrisBlock> tetrisBlocks = new ArrayList<>();

        Body body = iPhysicsGeneration.createTetrisElementBody(worldPositions, tetrisGridWidth, tetrisGridWidth);
        tetrisElement.setBody(body);
        tetrisElement.setPlayerOwningState(TetrisElementDomainContants.PLAYER_OWNED);
        body.setUserData(tetrisElement);

        tetrisElement.setInitialOrientation(new Vector2(body.getTransform().getOrientation()));

        List<Sprite> elementSprites = iGraphicsGeneration.createTetrisBlockSprite(worldPositions, tetrisGridWidth);

        for (Sprite sprite :
                elementSprites) {
            TetrisBlock tetrisBlock = this.iTetBlockPoolable.getBlockInstance();
            tetrisBlock.setSprite(sprite);
            tetrisBlock.setTetrisElementReference(tetrisElement);
            tetrisBlocks.add(tetrisBlock);
        }

        //set fixtures
        for (TetrisBlock tetrisBlock :
                tetrisBlocks) {
            int index = tetrisBlocks.indexOf(tetrisBlock);
            Fixture tetrisBlockFixture = body.getFixtureList().get(index);
            tetrisBlockFixture.setUserData(tetrisBlock);
            tetrisBlock.setFixture(tetrisBlockFixture);
        }


        if (this.iShapeDebugger == null) {
            this.iShapeDebugger = ShapeDebugger.getInstance();
        }
        this.iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
            @Override
            public Void apply(ShapeRenderer shapeRenderer) {
                if(tetrisElement.getBody() != null){

                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.line(tetrisElement.getBody().getTransform().getPosition(), new Vector2(
                            tetrisElement.getBody().getTransform().getPosition().x + tetrisElement.getBody().getTransform().getOrientation().x * 10,
                            tetrisElement.getBody().getTransform().getPosition().y + tetrisElement.getBody().getTransform().getOrientation().y * 10
                    ));
                    shapeRenderer.end();
                }
                return null;
            }
        }, 10);

        tetrisElement.setTetrisBlocks(tetrisBlocks);
        return tetrisElement;
    }

    @Override
    public GridLimits createTetrisGrid(Rectangle bottomPos, Rectangle leftPos, Rectangle rightPos) {
        GridLimits gridLimits = new GridLimits();

        RectangleLimit bottom = new RectangleLimit();
        bottom.setBody(iPhysicsGeneration.createGridLimitRectangle(new Vector2(bottomPos.x, bottomPos.y), bottomPos.width, bottomPos.height));

        RectangleLimit left = new RectangleLimit();
        left.setBody(iPhysicsGeneration.createGridLimitRectangle(new Vector2(leftPos.x, leftPos.y), leftPos.width, leftPos.height));

        RectangleLimit right = new RectangleLimit();
        right.setBody(iPhysicsGeneration.createGridLimitRectangle(new Vector2(rightPos.x, rightPos.y), rightPos.width, rightPos.height));

        gridLimits.setBottomLimit(bottom);
        gridLimits.setLeftLimit(left);
        gridLimits.setRightLimit(right);

        return gridLimits;
    }

}
