package com.ldz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ldz.impl.*;
import com.ldz.itf.*;
import com.ldz.tet.element.vol.calc.impl.TetElementVolumeCalculation;
import com.ldz.tet.element.vol.calc.itf.ITetElementVolumeCalculation;
import com.ldz.tet.shape.debugger.impl.ShapeDebugger;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class AnotherTetris extends ApplicationAdapter {

    private ITetrisPhysicsWorld iTetrisPhysicsWorld;
    private IShapeDebugger iShapeDebugger;
    private ITetrisElementGenerator iTetrisElementGenerator;
    private ITetElementVolumeCalculation iTetElementVolumeCalculation;
    private ITetElementToPositionnedSprite iTetElementToPositionnedSprite;

    private Camera camera;
    private Viewport viewport;

    private TetrisElement tetrisElement;
    private Polyline cuttedLine;

    private List<Polygon> customPolygons = new ArrayList<>();

    public AnotherTetris() {
    }

    @Override
    public void create() {
        this.camera = new OrthographicCamera(30, 30);
        this.viewport = new FitViewport(400, 400, this.camera);
        this.viewport.apply();
        this.iShapeDebugger = ShapeDebugger.getInstance();
        this.iTetrisElementGenerator = TetrisElementGenerator.getInstance();
        this.tetrisElement = this.iTetrisElementGenerator.createTetrisElement(Arrays.asList(new Vector2(0, 0), new
                        Vector2(40, 40),
                new Vector2(-40, 20), new Vector2(80, 60)), 30, new TetrisElement());
        this.iTetElementToPositionnedSprite = TetElementToPositionnedSprite.getInstance();
        this.iTetElementVolumeCalculation = TetElementVolumeCalculation.getInstance();
        this.customPolygons.add(this.registerPolygon(new Polygon(new float[]{
                -15, -15, 0, 15, 15, 15
        })));


        this.cuttedLine = new Polyline(new float[]{
                -Gdx.graphics.getWidth() / 2, -40f, Gdx.graphics.getWidth() / 2, 20f
        });
        this.cuttedLine.setOrigin(0, 0);
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
        this.tetrisElement.getBody().setTransform(this.tetrisElement.getBody().getTransform().getPosition(), this
                .tetrisElement.getBody().getTransform().getRotation() + Gdx.graphics.getDeltaTime());

        List<Polygon> polygons = this.iTetElementToPositionnedSprite.tetrisElementToPolygon(this.tetrisElement);
        this.cuttedLine.rotate(Gdx.graphics.getDeltaTime() * 9);

        for (Polygon polygon :
                polygons) {
            this.iTetElementVolumeCalculation.splitPolygonFromLine(polygon, new Polyline(this.cuttedLine
                    .getTransformedVertices()));
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }


    private Polygon registerPolygon(Polygon polygon) {


        if (this.iTetrisPhysicsWorld == null) {
            this.iTetrisPhysicsWorld = TetrisWorld.getInstance();
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = iTetrisPhysicsWorld.registerBody(bodyDef);

        body.setTransform(new Vector2(0, 0), 0);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(polygon.getTransformedVertices());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        Fixture fixture = body.createFixture(polygonShape, 100000f);
        fixture.setRestitution(0f);
        polygonShape.dispose();

        return polygon;


    }

}
