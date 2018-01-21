package com.ldz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ldz.impl.*;
import com.ldz.itf.*;

import java.util.Arrays;
import java.util.List;

public class AnotherTetris extends ApplicationAdapter {

    private ITetrisPhysicsWorld iTetrisPhysicsWorld;
    private IShapeDebugger iShapeDebugger;
    private ITetrisElementGenerator iTetrisElementGenerator;
    private ITetElementVolumeCalculation iTetElementVolumeCalculation;
    private ITetElementToPositionnedSprite iTetElementToPositionnedSprite;

    private Camera camera;
    private Viewport viewport;

    private TetrisElement tetrisElement;

    public AnotherTetris() {
    }
    @Override
    public void create() {
        this.camera = new OrthographicCamera(30, 30);
        this.viewport = new FitViewport(400, 400, this.camera);
        this.viewport.apply();
        this.iShapeDebugger = ShapeDebugger.getInstance();
        this.iTetrisElementGenerator = TetrisElementGenerator.getInstance();
        this.tetrisElement = this.iTetrisElementGenerator.createTetrisElement(Arrays.asList(new Vector2(0,0), new Vector2(10,10)), 30, new TetrisElement());
        this.iTetElementToPositionnedSprite = TetElementToPositionnedSprite.getInstance();
        this.iTetElementVolumeCalculation = TetElementVolumeCalculation.getInstance();
    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        this.iShapeDebugger.render(camera, delta);


        if (this.iTetrisPhysicsWorld == null) {
            this.iTetrisPhysicsWorld = TetrisWorld.getInstance();
        }

        this.iTetrisPhysicsWorld.render(camera);
        this.tetrisElement.getBody().setTransform(this.tetrisElement.getBody().getTransform().getPosition(), this.tetrisElement.getBody().getTransform().getRotation() + Gdx.graphics.getDeltaTime());

        List<Polygon> polygons = this.iTetElementToPositionnedSprite.tetrisElementToPolygon(this.tetrisElement);

        Polyline line = new Polyline(new float[]{
                -Gdx.graphics.getWidth()/2, -40f, Gdx.graphics.getWidth()/2, 20f
        });

        for (Polygon polygon :
                polygons) {
            this.iTetElementVolumeCalculation.splitPolygonFromLine(polygon, line);

        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }
}
