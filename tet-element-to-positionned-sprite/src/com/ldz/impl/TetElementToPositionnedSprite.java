package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.ldz.itf.IShapeDebugger;
import com.ldz.itf.ITetElementToPositionnedSprite;
import com.ldz.itf.TetrisBlock;
import com.ldz.itf.TetrisElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TetElementToPositionnedSprite implements ITetElementToPositionnedSprite {

    private static ITetElementToPositionnedSprite instance;
    private IShapeDebugger iShapeDebugger;

    private TetElementToPositionnedSprite() {
    }

    public static ITetElementToPositionnedSprite getInstance() {
        if (instance == null) {
            instance = new TetElementToPositionnedSprite();
        }
        return instance;
    }

    @Override
    public List<Sprite> getSpritesFromTetrisElement(TetrisElement tetrisElement) {

        List<Sprite> sprites = new ArrayList<>();
        if (tetrisElement != null) {

            for (TetrisBlock tetrisBlock :
                    tetrisElement.getTetrisBlocks()) {
                Sprite sprite = tetrisBlock.getSprite();
                sprite.setCenter(tetrisElement.getBody().getPosition().x, tetrisElement.getBody().getPosition().y);
                adjustSpritePosition(tetrisBlock, tetrisElement);
                sprites.add(tetrisBlock.getSprite());
            }
        }

        return sprites;
    }

    private void adjustSpritePosition(TetrisBlock tetrisBlock, TetrisElement tetrisElement) {

        //origin point
        Transform originTransform = tetrisElement.getBody().getTransform();

        Shape tetBlockShape = tetrisBlock.getFixture().getShape();

        if (tetBlockShape instanceof PolygonShape) {
            int vertexCount = ((PolygonShape) tetBlockShape).getVertexCount();

            if (vertexCount > 0) {
                Vector2 diffPosition = new Vector2();
                ((PolygonShape) tetBlockShape).getVertex(0, diffPosition);


                //polygon creation
                float[] vertext = new float[vertexCount * 2];
                for (int i = 0; i < vertexCount; i++) {
                    Vector2 currentPoint = new Vector2();
                    ((PolygonShape) tetBlockShape).getVertex(i, currentPoint);
                    vertext[i * 2] = originTransform.getPosition().x + currentPoint.x;
                    vertext[(i * 2) + 1] = originTransform.getPosition().y + currentPoint.y;
                }
                Polygon blockPolygon = new Polygon(vertext);

                Vector2 centerVector = new Vector2();
                blockPolygon.getBoundingRectangle().getCenter(centerVector);

                Vector2 positionWithoutRotation = new Vector2(
                        centerVector);

                Vector2 initialOrientation = tetrisElement.getInitialOrientation().nor();
                Vector2 actualOrientation = new Vector2(originTransform.getOrientation()).nor();

                double diffAngleRad = new Vector2(initialOrientation).angleRad(new Vector2(actualOrientation));

                if (iShapeDebugger == null) {
                    iShapeDebugger = ShapeDebugger.getInstance();
                }

                iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
                    @Override
                    public Void apply(ShapeRenderer shapeRenderer) {
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

                        if ((float) (diffAngleRad * MathUtils.radiansToDegrees) >= 0) {
                            shapeRenderer.setColor(Color.BLUE);
                        } else {
                            shapeRenderer.setColor(Color.RED);
                        }

                        shapeRenderer.arc(positionWithoutRotation.x, positionWithoutRotation.y, 10, 0, (float) (diffAngleRad * MathUtils.radiansToDegrees));
                        shapeRenderer.end();
                        return null;
                    }
                }, Gdx.graphics.getDeltaTime());

                Vector2 positionWithRotation = new Vector2(
                        (float) (Math.cos(diffAngleRad) * (positionWithoutRotation.x - originTransform.getPosition().x) - Math.sin(diffAngleRad) * (positionWithoutRotation.y - originTransform.getPosition().y) + originTransform.getPosition().x),
                        (float) (Math.sin(diffAngleRad) * (positionWithoutRotation.x - originTransform.getPosition().x) + Math.cos(diffAngleRad) * (positionWithoutRotation.y - originTransform.getPosition().y) + originTransform.getPosition().y)
                );


                tetrisBlock.getSprite().setCenter(positionWithRotation.x, positionWithRotation.y);
                tetrisBlock.getSprite().setRotation(0);
                tetrisBlock.getSprite().rotate((float) diffAngleRad * MathUtils.radiansToDegrees);
            }
        }
    }
}
