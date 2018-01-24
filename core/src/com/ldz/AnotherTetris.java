package com.ldz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ldz.impl.*;
import com.ldz.itf.*;

public class AnotherTetris extends ApplicationAdapter {

    private ITetrisPhysicsWorld iTetrisPhysicsWorld;
    private IGraphicContext iGraphicContext;
    private ITetrisGrid iTetrisGrid;
    private ITetrisRewardDetection iTetrisRewardDetection;
    private IElementPlayerManager iElementPlayerManager;
    private IShapeDebugger iShapeDebugger;
    private IElementEraser iElementEraser;
    private IElementSpawn iElementSpawn;

    private Camera camera;
    private Viewport viewport;

    public AnotherTetris() {
        this.iTetrisGrid = TetrisGrid.getInstance(); }

    @Override
    public void create() {
        this.camera = new OrthographicCamera(30, 30);
        this.viewport = new FillViewport(400, 400, this.camera);
        this.iGraphicContext = GraphicsContext.getInstance(new SpriteBatch());
        this.viewport.apply();
        this.iTetrisRewardDetection = TetrisRewardDetection.getInstance();
        this.iShapeDebugger = ShapeDebugger.getInstance();
        this.iElementSpawn = ElementSpawn.getInstance();

        IGridInitializer iGridInitializer = GridInitializer.getInstance();
        iGridInitializer.initializeGrid();

    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.iElementPlayerManager == null) {
            this.iElementPlayerManager = ElementPlayerManager.getInstance();
        }

        this.iGraphicContext.getSpriteBatch().setProjectionMatrix(camera.combined);
        this.iGraphicContext.getSpriteBatch().begin();
        float delta = Gdx.graphics.getDeltaTime();
        this.iElementPlayerManager.render(delta);
        this.iTetrisGrid.renderAndUpdate(delta);
        this.iGraphicContext.getSpriteBatch().end();

        this.iShapeDebugger.render(camera, delta);

        this.iTetrisRewardDetection.checkRewardAndSpawn();

        if (Gdx.input.justTouched()) {
            this.iElementSpawn.spawnElement();
        }
        if (this.iTetrisPhysicsWorld == null) {
            this.iTetrisPhysicsWorld = TetrisWorld.getInstance();
        }


        this.iTetrisPhysicsWorld.render(camera);

        if(this.iElementEraser == null){
            this.iElementEraser = ElementEraser.getInstance();
        }
        this.iElementEraser.eraseAllElements();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }
}
